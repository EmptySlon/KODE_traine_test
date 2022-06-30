package com.emptyslon.kode

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emptyslon.kode.common.Common
import com.emptyslon.kode.contract.EmployeesDataBaseFun
import com.emptyslon.kode.contract.navigator
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.databinding.FragmentListUserBinding
import com.emptyslon.kode.retrofit.RetrofitClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_list_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val DRAWABLE_RIGHT = 2

class ListUserFragment : Fragment(), EmployeesDataBaseFun {
    lateinit var binding: FragmentListUserBinding
    private lateinit var options: Options
    var inputEmployees = listOf<Employee>()
    lateinit var filteredEmployees: List<Employee>
    lateinit var typeSorted: String
    lateinit var valueTad: String

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AdapterEmploees


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }


    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListUserBinding.inflate(inflater, container, false)
        AddTabInTabLayout()

        typeSorted = getString(R.string.sorted_alphabet)
        valueTad = Common.listDepartment.first()

        recyclerView = binding.recycleListUser
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = AdapterEmploees(inputEmployees)
        recyclerView.adapter = adapter

        getData()

        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                valueTad = tab!!.text.toString()
                val valueSearch = binding.inputSearch.text.toString()
                filteredEmployees = inputEmployees
                    .filterFromDepartment(valueTad)
                    .searchEmployees(valueSearch)
                refreshEmptySearchHolder()
                adapter.listEmployees = filteredEmployees
                adapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.swipeRefresh.setOnRefreshListener {
            binding.inputSearch
                .compoundDrawables[DRAWABLE_RIGHT]
                .setTint(requireActivity().getColor(R.color.grey2))
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ListUserFragment())
                .commit()
        }

        binding.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.v("TAP", "tap!!")
                filteredEmployees = inputEmployees
                    .searchEmployees(s.toString())
                    .filterFromDepartment(valueTad)
                adapter.listEmployees = filteredEmployees
                adapter.notifyDataSetChanged()
                refreshEmptySearchHolder()
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        binding.inputSearch.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= inputSearch.right - inputSearch.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    showAlertOfSorted(inputSearch.compoundDrawables[DRAWABLE_RIGHT])
                    return@OnTouchListener true
                }
            }
            false
        })


        return binding.root
    }

    private fun refreshEmptySearchHolder() {
        with(binding.emptySearchHolder) {
            visibility = if (filteredEmployees.isEmpty()) View.VISIBLE
            else View.GONE
        }
    }


    private fun getData() {
        val retrofitData = RetrofitClient.retrofit.getData()
        retrofitData.enqueue(object : Callback<EmployeesData?> {
            override fun onResponse(
                call: Call<EmployeesData?>,
                response: Response<EmployeesData?>
            ) {
                EmployeesDataBase.deleteEmployees()
                val listEmployees = response.body()?.employees!!
                listEmployees.map { it.avatarUrl = Common.listUrl.random() }

                EmployeesDataBase.listEmployees = listEmployees.toMutableList()
                inputEmployees = listEmployees.sortedByType(typeSorted)
                filteredEmployees = inputEmployees

                adapter.listEmployees = inputEmployees
                adapter.notifyDataSetChanged()
                binding.progressBar.visibility = ProgressBar.GONE
            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                navigator().showErrorFragment()

            }
        })
    }


    companion object {
        @JvmStatic
        private val KEY_OPTIONS = "OPTIONS"
    }

    private fun AddTabInTabLayout() {
        for (department in Common.listDepartment) {
            val newTab = binding.tabCategory.newTab()
            newTab.text = department
            binding.tabCategory.addTab(newTab)
        }
    }

    private fun showAlertOfSorted(drawable: Drawable) {
        val listTypeSorters =
            arrayOf(getString(R.string.sorted_alphabet), getString(R.string.sorted_birthday))
        val checkedItem = listTypeSorters.indexOf(typeSorted)

        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.sorting))
            .setSingleChoiceItems(listTypeSorters, checkedItem) { dialogInterface, i ->
                typeSorted = listTypeSorters[i]
                inputEmployees = inputEmployees.sortedByType(typeSorted)
                filteredEmployees = inputEmployees.filterFromDepartment(valueTad)
                adapter.listEmployees = filteredEmployees
                adapter.isSortedByBirthday = typeSorted != listTypeSorters.first()
                adapter.notifyDataSetChanged()
                if (typeSorted == getString(R.string.sorted_birthday)) {
                    drawable.setTint(requireActivity().getColor(R.color.purple_700))
                } else drawable.setTint(requireActivity().getColor(R.color.grey2))
                dialogInterface.dismiss()
            }
            .show()

    }


}
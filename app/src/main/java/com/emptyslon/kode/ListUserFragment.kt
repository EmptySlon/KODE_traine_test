package com.emptyslon.kode

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emptyslon.kode.common.Common
import com.emptyslon.kode.contract.EmployeesDataBaseFun
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.databinding.FragmentListUserBinding
import com.emptyslon.kode.retrofit.RetrofitClient
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_list_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserFragment() : Fragment(), EmployeesDataBaseFun {
    lateinit var binding: FragmentListUserBinding
    private lateinit var options: Options
    var inputEmployees = listOf<Employee>()
    lateinit var filteredEmployees: List<Employee>

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AdapterEmploees


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListUserBinding.inflate(inflater, container, false)
        AddTabInTabLayout()

        recyclerView = binding.recycleListUser
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = AdapterEmploees(inputEmployees)
        recyclerView.adapter = adapter

        getData()

        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val valueTad = tab!!.text.toString()
                filteredEmployees = inputEmployees.filterFromDepartment(valueTad)
                adapter.listEmployees = filteredEmployees
                adapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.swipeRefresh.setOnRefreshListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ListUserFragment())
                .commit()
        }

        binding.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.v("TAP", "tap!!")
                filteredEmployees = inputEmployees.searchEmployees(s.toString())
                adapter.listEmployees = filteredEmployees
                adapter.notifyDataSetChanged()
                with(binding.emptySearchHolder) {
                    visibility = if (filteredEmployees.isEmpty()) View.VISIBLE
                    else View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })


        return binding.root
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
                inputEmployees =
                    listEmployees.sortedByType(binding.root.context.getString(R.string.sorted_alphabet))

                adapter.listEmployees = inputEmployees
                adapter.notifyDataSetChanged()
                binding.progressBar.visibility = ProgressBar.GONE
            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                Log.v("DATA", "NOT GET DATA!!!!!! ERROR!!!!!!!!!!!!!!!!!!")

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


}
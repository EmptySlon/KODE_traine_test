package com.emptyslon.kode

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.emptyslon.kode.common.Common
import com.emptyslon.kode.common.Common.Companion.typeSorted
import com.emptyslon.kode.contract.EmployeesDataBaseFun
import com.emptyslon.kode.contract.navigator
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.dataBase.EmployeesDataBase.Companion.listEmployees
import com.emptyslon.kode.databinding.FragmentListUserBinding
import com.emptyslon.kode.retrofit.RetrofitClient
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserFragment() : Fragment(), EmployeesDataBaseFun {
    lateinit var binding: FragmentListUserBinding
    private lateinit var options: Options
    var inputEmployees = mutableListOf<Employee>()
    lateinit var filteredEmployees: List<Employee>

    lateinit var tabLayout: TabLayout
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AdapterEmploees
    lateinit var currentTad: String



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

        getData()

        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val valueTad = tab!!.text.toString()
                filteredEmployees = inputEmployees.filterFromDepartment(valueTad)
                adapter = AdapterEmploees(filteredEmployees)
                recyclerView.adapter = adapter
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
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
                inputEmployees = listEmployees.toMutableList().sortedByType(binding.root.context.getString(R.string.sorted_alphabet))
                adapter = AdapterEmploees(inputEmployees)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
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

    private fun refreshListInAdapter(listUser: List<Employee> ) {
        adapter = AdapterEmploees(listUser )
        adapter.setonItemClickListener(object : AdapterEmploees.onItemClickListener{
            override fun onItemClick(employee: Employee) {

                val detailsEmployeeFragment = DetailsEmployeeFragment(employee)
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, detailsEmployeeFragment)
                    .commit()
            }

        })
        recyclerView.adapter = adapter
    }




}
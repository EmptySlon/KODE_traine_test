package com.emptyslon.kode

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.emptyslon.kode.common.Common
import com.emptyslon.kode.common.Common.Companion.typeSorted
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.retrofit.RetrofitClient
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserFragment(var listUser: List<Employee>) : Fragment() {
    lateinit var tabLayout: TabLayout
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AdapterEmploees

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_user, container, false)
        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycleListUser)
        adapter = AdapterEmploees(listUser)
        adapter.setonItemClickListener(object : AdapterEmploees.onItemClickListener{
            override fun onItemClick(position: Int) {
                val employee = listUser[position]
                val detailsEmployeeFragment = DetailsEmployeeFragment(employee)
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.placeHolderListUsers, detailsEmployeeFragment)
                    .commit()
            }

        })
        tabLayout = activity?.findViewById<TabLayout>(R.id.tabCategory)!!

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            onRefresh(adapter, swipeRefresh)
        }
        return view
    }

    fun refreshListData(listUser: List<Employee>, tab: String) {
        adapter = AdapterEmploees(listUser,tab )
        adapter.setonItemClickListener(object : AdapterEmploees.onItemClickListener{
            override fun onItemClick(position: Int) {
                val employee = listUser[position]
                val detailsEmployeeFragment = DetailsEmployeeFragment(employee)
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.placeHolderListUsers, detailsEmployeeFragment)
                    .commit()
            }

        })
        recyclerView.adapter = adapter
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun onRefresh(adapter: AdapterEmploees, swipeRefresh: SwipeRefreshLayout) {
        val retrofitData = RetrofitClient.retrofit.getData()

        retrofitData.enqueue(object : Callback<EmployeesData?> {
            override fun onResponse(
                call: Call<EmployeesData?>,
                response: Response<EmployeesData?>
            ) {
                listUser = listOf()
                EmployeesDataBase.deleteEmployees()
                val listEmployees = response.body()?.employees!!
                listEmployees.map { it.avatarUrl = Common.listUrl.random() }
                EmployeesDataBase.listEmployees = listEmployees.toMutableList()
                EmployeesDataBase.sortedByType(typeSorted)

                val currentTad = tabLayout.getTabAt(tabLayout.selectedTabPosition)!!.text.toString()

                adapter.listEmployees = EmployeesDataBase.getListEmployeesFromDepartment(currentTad)
                swipeRefresh.isRefreshing = false
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                activity!!.findViewById<FrameLayout>(R.id.err_window).visibility = View.VISIBLE
            }
        })
    }

}
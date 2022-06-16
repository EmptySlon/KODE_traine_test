package com.emptyslon.kode

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.emptyslon.kode.common.Common
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.dataBase.EmployeesDataBase.Companion.listEmployees
import com.emptyslon.kode.retrofit.RetrofitClient
import com.github.javafaker.Faker
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
                var listEmployees = response.body()?.employees!!
                listEmployees = if (Common.typeSorted == getString(R.string.sorted_alphabet)) {
                    listEmployees.sortedBy { it.firstName }
                } else listEmployees.sortedBy { it.birthday }
                listEmployees.map { it.avatarUrl = Common.listUrl.random() }
                listEmployees.map { EmployeesDataBase.listEmployees.add(it) }

                val currentTad = tabLayout.getTabAt(tabLayout.selectedTabPosition)!!.text
                listUser = if (currentTad == "all") listEmployees
                else listEmployees.filter { it.department == currentTad }

                adapter.listCategories = listUser
                swipeRefresh.isRefreshing = false
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                activity!!.findViewById<FrameLayout>(R.id.err_window).visibility = View.VISIBLE
            }
        })
    }

}
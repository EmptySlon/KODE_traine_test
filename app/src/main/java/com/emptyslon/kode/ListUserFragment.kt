package com.emptyslon.kode

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.retrofit.RetrofitClient
import com.github.javafaker.Faker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserFragment(var listUser: List<Employee>) : Fragment() {

//    private val listUser =
//        listOf<String>("All", "Designers", "Analysts", "Managers", "IOS", "Android")


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_user, container, false)
        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleListUser)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = AdapterEmploees(listUser)
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            onRefresh(adapter)
        }


//        val userListViewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)
//        userListViewModel.fetchUserList((activity?.application as? KodeApp)?.userApi)


        return view
    }

    private fun onRefresh(adapter: AdapterEmploees) {
        val retrofitData = RetrofitClient.retrofit.getData()

        retrofitData.enqueue(object : Callback<EmployeesData?> {
            override fun onResponse(
                call: Call<EmployeesData?>,
                response: Response<EmployeesData?>
            ) {
                listUser = listOf()
                val faker = Faker()
                val listEmployees = response.body()?.employees!!
                listEmployees.map { it.avatarUrl = faker.avatar().image() }
                listEmployees.map { EmployeesDataBase.listEmployees.add(it) }
//                listUser =  response.body()?.employees!!
                listUser = listOf(
                    Employee(
                        "",
                        "25.45.25",
                        "Designers",
                        "sdf",
                        "sdfsdf",
                        "asdfdasf",
                        "5645613621",
                        "sdff",
                        "dds"
                    )
                )

                adapter.notifyDataSetChanged()




            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                Log.v("TAG", "message from onFailure: " + t.message)
            }
        })


    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance() = ListUserFragment()
//
//    }
}
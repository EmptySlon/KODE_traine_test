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

class ListUserFragment() : Fragment() {
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

        recyclerView = binding.recycleListUser
        recyclerView.layoutManager = LinearLayoutManager(activity)
        Log.v("asdasd", "!!!!!!!!!!!!!!!!!!!!!!!")





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

//                listEmployees.forEach { inputEmployees.add(it) }
//                listEmployees.forEach { Log.v("asdasd", it.firstName) }
                inputEmployees = listEmployees.toMutableList()
                adapter = AdapterEmploees(inputEmployees)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter


            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                Log.v("DATA", "NOT GET DATA!!!!!! ERROR!!!!!!!!!!!!!!!!!!")

            }
        })

        if (inputEmployees.isEmpty()) Log.v("DATA", "inputEmployees is EMPTY")
        else inputEmployees.forEach { Log.v("asdasd", it.firstName) }










//        inputEmployees = inputEmployees + listOf<Employee>(
//            Employee(
//                "asdasd", "15.15.15", "asd", "asdf",
//                "adsf", "dasf", "dsfdf", "sdfs", "sdf"
//            )
//        )



//        navigator().listenResult(Options::class.java, viewLifecycleOwner) {
//            this.options = it
//        }

        return binding.root
    }

    private fun getData() {

    }


    companion object {
        @JvmStatic
        private val KEY_OPTIONS = "OPTIONS"
    }


}
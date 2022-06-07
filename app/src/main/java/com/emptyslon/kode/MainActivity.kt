package com.emptyslon.kode

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.databinding.ActivityMainBinding
import com.emptyslon.kode.retrofit.RetrofitClient
import com.google.android.material.tabs.TabLayout
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val BASE_URL = "https://stoplight.io/mocks/kode-education/trainee-test/25143926/"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapterCategory: AdapterHeaderCategories
    val listCategories =
        listOf<String>("All", "Designers", "Analysts", "Managers", "IOS", "Android")
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val tab = inflater.inflate(R.layout.fragment_list_user, container, false)
//        val tab = TabLayout.Tab()
//        tab.text = "126"
//        binding.tabCategory.addTab(tab)
        val employeesDataBase = EmployeesDataBase()
//        val  fistEmployee = employeesDataBase.listEmployees.first().firstName
//        Log.v("TAG", fistEmployee)

        val retrofitData = RetrofitClient.retrofit.getData()

        retrofitData.enqueue(object : Callback<EmployeesData?> {
            override fun onResponse(
                call: Call<EmployeesData?>,
                response: Response<EmployeesData?>
            ) {
                val listEmployees1 = response.body()?.employees!!
                val listName = listEmployees1.map { it.firstName }
                listEmployees1.map { EmployeesDataBase.listEmployees.add(it) }
//                for(employee in listEmployees1) {
//                    Log.v("TAG", employee.firstName)
//                }
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        ListUserFragment(listName)
                    ).commit()


            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                Log.v("TAG", "message from onFailure: " + t.message)
            }
        })

        if ( EmployeesDataBase.listEmployees.isEmpty()) {
            Log.v("TAG", "EmployeesDataBase.listEmployees is empty")
        }

        for (employee in EmployeesDataBase.listEmployees ) {
            Log.v("TAG", "Employees from MainActivity: $employee")
        }











        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        ListUserFragment(listCategories + listOf(counter++.toString()))
                    ).commit()

                for (employee in EmployeesDataBase.listEmployees ) {
                    Log.v("TAG", "Employees from MainActivity: ${employee.birthday}")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        val newTab = binding.tabCategory.newTab()
        newTab.text = "153"
//        binding.tabCategory.addTab(newTab)


//        adapterCategory = AdapterHeaderCategories(this, listCategories)
//        binding.recycleCategory.adapter = adapterCategory
//        binding.recycleCategory.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


    }


}


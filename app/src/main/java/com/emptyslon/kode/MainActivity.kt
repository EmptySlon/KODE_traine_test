package com.emptyslon.kode

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.databinding.ActivityMainBinding
import com.emptyslon.kode.retrofit.RetrofitClient
import com.github.javafaker.Faker
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val BASE_URL = "https://stoplight.io/mocks/kode-education/trainee-test/25143926/"


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapterCategory: AdapterEmploees
    private val listDepartment =
        listOf<String>(
            "all", "android", "ios", "design", "management", "qa", "back_office",
            "frontend", "hr", "pr", "backend", "support", "analytics",
        )
    var counter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (department in listDepartment) {
            val newTab = binding.tabCategory.newTab()
            newTab.text = department
            binding.tabCategory.addTab(newTab)
        }




        val retrofitData = RetrofitClient.retrofit.getData()

        retrofitData.enqueue(object : Callback<EmployeesData?> {
            override fun onResponse(
                call: Call<EmployeesData?>,
                response: Response<EmployeesData?>
            ) {
                val faker = Faker()
                val listEmployees = response.body()?.employees!!
                listEmployees.map { it.avatarUrl = faker.avatar().image() }
                listEmployees.map { EmployeesDataBase.listEmployees.add(it) }

//                }
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        ListUserFragment(listEmployees)
                    ).commit()

                binding.progressBar.visibility = ProgressBar.GONE


            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                Log.v("TAG", "message from onFailure: " + t.message)
            }
        })

        if (EmployeesDataBase.listEmployees.isEmpty()) {
            Log.v("TAG", "EmployeesDataBase.listEmployees is empty")
        }

        for (employee in EmployeesDataBase.listEmployees) {
            Log.v("TAG", "Employees from MainActivity: $employee")
        }



        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        ListUserFragment(EmployeesDataBase.getListEmployeesFromDepartment(tab!!.text.toString()))
                    ).commit()

                for (employee in EmployeesDataBase.listEmployees) {
                    Log.v("TAG", "Employees from MainActivity: ${employee.birthday}")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        for (inputDepartment in EmployeesDataBase.getListAllDepartment()) {
            if (inputDepartment !in listDepartment) {
                val newTab = binding.tabCategory.newTab()
                newTab.text = inputDepartment
                binding.tabCategory.addTab(newTab)
            }
        }


//        adapterCategory = AdapterHeaderCategories(this, listCategories)
//        binding.recycleCategory.adapter = adapterCategory
//        binding.recycleCategory.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


    }


}


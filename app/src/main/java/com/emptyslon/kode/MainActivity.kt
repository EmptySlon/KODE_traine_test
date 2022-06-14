package com.emptyslon.kode

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.emptyslon.kode.common.Common
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
        val tabLayout = binding.tabCategory

        for (department in listDepartment) {
            val newTab = binding.tabCategory.newTab()
            newTab.text = department
            binding.tabCategory.addTab(newTab)
        }

        getData()

        binding.errWindow.findViewById<TextView>(R.id.err_tx_rebut).setOnClickListener {
            binding.errWindow.visibility = View.GONE
            getData()
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

    }

    private fun getData() {
        val retrofitData = RetrofitClient.retrofit.getData()

        retrofitData.enqueue(object : Callback<EmployeesData?> {
            override fun onResponse(
                call: Call<EmployeesData?>,
                response: Response<EmployeesData?>
            ) {
                val faker = Faker()
                val listEmployees = response.body()?.employees!!.sortedBy { it.firstName }

                listEmployees.map { it.avatarUrl = Common().listUrl.random() }
                listEmployees.map { EmployeesDataBase.listEmployees.add(it) }
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        ListUserFragment(listEmployees)
                    ).commit()

                binding.progressBar.visibility = ProgressBar.GONE
            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                binding.errWindow.visibility = View.VISIBLE
            }
        })
    }


}


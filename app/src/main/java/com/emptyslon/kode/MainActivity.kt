package com.emptyslon.kode

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.emptyslon.kode.dataBase.Employees
import com.emptyslon.kode.dataBase.EmployeesApi
import com.emptyslon.kode.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

        getEmployeesData()







        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        ListUserFragment(listCategories + listOf(counter++.toString()))
                    ).commit()
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

    private fun getEmployeesData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(EmployeesApi::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<Employees>?> {
            override fun onResponse(
                call: Call<List<Employees>?>,
                response: Response<List<Employees>?>
            ) {
                val responseBody = response.body()!!
                val myStringBuilder = StringBuilder()
                for (employees in responseBody) {
                    myStringBuilder.append(employees.firstName)
                    myStringBuilder.append("\n")
                    Log.d("MyTag", myStringBuilder.toString() )

                }
            }

            override fun onFailure(call: Call<List<Employees>?>, t: Throwable) {
                Log.d("MyTag", "onFailure:" + t.message )
            }
        })
    }
}
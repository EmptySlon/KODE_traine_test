package com.emptyslon.kode.dataBase

import android.util.Log
import com.emptyslon.kode.retrofit.RetrofitClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EmployeesDataBase {
    var listEmployees: List<Employee>

    init {
        listEmployees = getEmployeesData()
    }

    companion object {
       val retrofitClient = RetrofitClient

    }


    private fun getEmployeesData(): List<Employee> {


//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(httpLoggingInterceptor)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://stoplight.io/mocks/kode-education/trainee-test/25143926/")
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//            .create(EmployeesApi::class.java)

        val retrofitData = retrofitClient.retrofit.getData()

        retrofitData.enqueue(object : Callback<EmployeesData?> {
            override fun onResponse(
                call: Call<EmployeesData?>,
                response: Response<EmployeesData?>
            ) {
                val listEmployees1 = response.body()?.employees!!
                for(employee in listEmployees1) {
                    Log.v("TAG", employee.firstName)
                }
                this@EmployeesDataBase.listEmployees = response.body()?.employees!!
            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                Log.v("TAG", "message from onFailure: " + t.message)
            }
        })

        return listEmployees
    }

}
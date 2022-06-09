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

    companion object {
        val listEmployees = mutableListOf<Employee>()
        fun getListAllDepartment(): List<String> =
            listEmployees.map { it.department }.toSet().toList()

        fun getListEmployeesFromDepartment(department: String): List<Employee> =
            listEmployees.filter { it.department.uppercase() == department.uppercase() }

    }


}
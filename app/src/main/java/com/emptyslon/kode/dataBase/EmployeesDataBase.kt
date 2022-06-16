package com.emptyslon.kode.dataBase

import android.util.Log
import com.emptyslon.kode.common.Common
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
        var listEmployees = mutableListOf<Employee>()
        fun getListAllDepartment(): List<String> =
            listEmployees.map { it.department }.toSet().toList()

        fun deleteEmployees() = listEmployees.clear()


        fun getListEmployeesFromDepartment(department: String): List<Employee> {
            return if (department.uppercase() == "ALL") listEmployees
            else listEmployees.filter { it.department.uppercase() == department.uppercase() }
        }

        fun searchEmployees(subString: String): List<Employee> {
            return listEmployees.filter { employee ->
                employee.isContainsSubstring(subString)
            }

        }

        fun sortedByType(type: String) {
            listEmployees =
                if (type == "По алфавиту") listEmployees.sortedBy { it.firstName }.toMutableList()
                else listEmployees.sortedBy { it.birthday }.toMutableList()
        }

    }


}
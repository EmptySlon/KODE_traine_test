package com.emptyslon.kode.dataBase

import com.emptyslon.kode.Interface.UserListResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface EmployeesApi {

    @GET("./users")
    @Headers("Content-Type: application/json", "Prefer: code=200, dynamic=true")
    fun getData(): Call<EmployeesData>
}
package com.emptyslon.kode.Interface

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserApi {

    @GET("./users")
    @Headers("Content-Type: application/json", "Prefer: code=200, dynamic=true")
    fun getUserList(): Single<UserListResponse>
}
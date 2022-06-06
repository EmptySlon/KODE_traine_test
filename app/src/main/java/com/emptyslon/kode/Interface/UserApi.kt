package com.emptyslon.kode.Interface

import io.reactivex.Single
import retrofit2.http.GET

interface UserApi {

    @GET("./")
    fun getUserList(): Single<UserListResponse>
}
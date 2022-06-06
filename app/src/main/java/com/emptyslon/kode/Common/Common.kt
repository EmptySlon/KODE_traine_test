package com.emptyslon.kode.Common

import com.emptyslon.kode.Interface.RetrofitServices
import com.emptyslon.kode.retrofit.RetrofitClient

object Common {
    private val BASE_URL = "https://stoplight.io/mocks/kode-education/trainee-test/25143926/users"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}
package com.example.e_bill.api

import com.example.e_bill.model.login.LoginBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type:application/json")
    @POST("login")
    fun checkLogin(@Body info: LoginBody): retrofit2.Call<ResponseBody>
}
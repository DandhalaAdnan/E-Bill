package com.example.e_bill.viewModel

import android.content.Context
import com.example.e_bill.api.ApiService
import com.example.e_bill.api.RemoteRetrofitInstance
import com.example.e_bill.model.login.LoginBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginDataSource(private val context: Context) {

    interface LoginCallBack {
        fun onSuccess()
        fun onError(message: String?)
    }

    fun login(email: String, password: String, loginCallBack: LoginCallBack) {
        RemoteRetrofitInstance.getRetrofitInstance().create(ApiService::class.java).checkLogin(
            LoginBody(email, password)
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loginCallBack.onError(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    loginCallBack.onSuccess()
                } else {
                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        loginCallBack.onError(jObjError.getString("user_msg"))

                    } catch (e: Exception) {
                        loginCallBack.onError(e.message)
                    }
                }
            }
        })
    }
}

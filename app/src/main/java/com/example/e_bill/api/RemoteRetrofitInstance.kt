package com.example.e_bill.api

import com.example.e_bill.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRetrofitInstance {
    companion object {
        private const val BASE_URL = "http://www.atplglobal.com/hingoli/api/"
        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .apply {
                    if (BuildConfig.DEBUG) {
                        this.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    }
                }
                .addInterceptor(getHeaderInterceptor())
                .build()
        }

        private fun getHeaderInterceptor(): Interceptor {
            return Interceptor { chain ->
                val request =
                    chain.request().newBuilder()
                        .header("Client-Service", "app-client")
                        .build()
                chain.proceed(request)
            }
        }

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
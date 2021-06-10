package com.app.githubuserappsub2.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServices {
    companion object {
        const val tokenAPI = "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"
        const val baseURL = "https://api.github.com/"

        fun getRetrofitInstance(): Retrofit {

            val logInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().apply {
                addInterceptor(logInterceptor)
                connectTimeout(20, TimeUnit.SECONDS)
                readTimeout(20, TimeUnit.SECONDS)
                writeTimeout(20, TimeUnit.SECONDS)
            }.build()
            return Retrofit.Builder().apply {
                baseUrl(baseURL)
                addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                client(client)
            }.build()
        }
    }
}
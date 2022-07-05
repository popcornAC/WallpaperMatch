package com.arikc.wallpapermatch.client

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UnsplashRetrofitClient {
    private const val BASE_URL = "https://api.unsplash.com"
    fun getInstance(): Retrofit {
        val mOkHttpClient = OkHttpClient
            .Builder().addInterceptor(Interceptor {
                val builder = it.request().newBuilder()
                builder.header("Accept-Version", "v1")
                builder.header("Authorization", "Client-ID INSERT HERE")
                return@Interceptor it.proceed(builder.build())
            })
            .build()
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).client(mOkHttpClient).build()
    }
}
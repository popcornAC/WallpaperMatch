package com.arikc.wallpapermatch.service

import android.util.Log
import com.arikc.wallpapermatch.api.UnsplashInterface
import com.arikc.wallpapermatch.client.UnsplashRetrofitClient
import java.lang.Exception

class UnsplashService(
    val unsplashClient: UnsplashInterface = UnsplashRetrofitClient.getInstance()
        .create(UnsplashInterface::class.java)
) {
    suspend fun getRandomPhoto(): String {
        try {
            val response = unsplashClient.getRandomPhoto()
            if(response.isSuccessful) {
                response.body()?.urls?.let { return(it.regular) }
            }
        } catch (Ex: Exception) {
            Ex.localizedMessage?.let { Log.e("lmao what happened here", it) }
        }
        return ""
    }
}
package com.arikc.wallpapermatch.service

import android.util.Log
import com.arikc.wallpapermatch.api.UnsplashInterface
import com.arikc.wallpapermatch.client.UnsplashRetrofitClient
import com.arikc.wallpapermatch.model.Photo
import java.lang.Exception

class UnsplashService(
    private val unsplashClient: UnsplashInterface = UnsplashRetrofitClient.getInstance()
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

    suspend fun getRandomPhotos(retrieveCount: Int = 30): List<Photo>? {
        try {
            val response = unsplashClient.getRandomPhotos(retrieveCount)
            if(response.isSuccessful) {
                return response.body()
            }
        } catch (Ex: Exception) {
            Ex.localizedMessage?.let { Log.e("lmao what happened here", it) }
            return null
        }
        return null
    }
}
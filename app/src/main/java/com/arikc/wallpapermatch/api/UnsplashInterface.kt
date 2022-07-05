package com.arikc.wallpapermatch.api

import com.arikc.wallpapermatch.model.Photo
import retrofit2.Response
import retrofit2.http.GET

interface UnsplashInterface {
    @GET("/photos/random")
    suspend fun getRandomPhoto(): Response<Photo>
}
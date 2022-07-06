package com.arikc.wallpapermatch.api

import com.arikc.wallpapermatch.model.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashInterface {
    @GET("/photos/random")
    suspend fun getRandomPhoto(): Response<Photo>

    @GET("/photos/random")
    suspend fun getRandomPhotos(@Query("count") retrieveCount: Int): Response<List<Photo>>
}
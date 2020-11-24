package com.example.testingpractice.data.remote

import com.example.testingpractice.BuildConfig
import com.example.testingpractice.data.remote.responses.PixabayApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") query: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ): PixabayApiResponse
}
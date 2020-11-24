package com.example.testingpractice.data.remote.responses


import com.google.gson.annotations.SerializedName

data class PixabayApiResponse(
    @SerializedName("hits")
    val hits: List<ImageResponse>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)
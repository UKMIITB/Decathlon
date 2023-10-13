package com.example.decathlon.common

import retrofit2.http.POST

interface DecathlonRetrofitService {

    @POST("/api/v1/home")
    suspend fun getHomeData()
}
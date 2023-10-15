package com.example.decathlon.common

import com.example.decathlon.homescreen.model.network.HomeItemsRequest
import com.example.decathlon.homescreen.model.network.HomeItemsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DecathlonRetrofitService {

    @POST("/api/v1/home")
    suspend fun getHomeData(@Body homeItemsRequest: HomeItemsRequest): Response<HomeItemsResponse>
}
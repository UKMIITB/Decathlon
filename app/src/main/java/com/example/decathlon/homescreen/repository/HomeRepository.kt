package com.example.decathlon.homescreen.repository

import com.example.decathlon.common.DecathlonRetrofitService
import com.example.decathlon.homescreen.model.network.HomeItemsRequest
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val decathlonRetrofitService: DecathlonRetrofitService
) {

    suspend fun getHomeData(homeItemsRequest: HomeItemsRequest) =
        decathlonRetrofitService.getHomeData(homeItemsRequest = homeItemsRequest)
}
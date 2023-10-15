package com.example.decathlon.homescreen.repository

import com.example.decathlon.BuildConfig
import com.example.decathlon.common.DecathlonRetrofitService
import com.example.decathlon.homescreen.model.network.HomeItemsRequest
import com.example.decathlon.homescreen.model.network.HomeItemsResponse
import com.example.decathlon.util.MockUtil
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val decathlonRetrofitService: DecathlonRetrofitService
) {

    suspend fun getHomeData(homeItemsRequest: HomeItemsRequest): Response<HomeItemsResponse> {
        return if (BuildConfig.IS_MOCK_ENABLED) {
            MockUtil.getMockedHomeData(homeItemsRequest = homeItemsRequest)
        } else {
            decathlonRetrofitService.getHomeData(homeItemsRequest = homeItemsRequest)
        }
    }
}
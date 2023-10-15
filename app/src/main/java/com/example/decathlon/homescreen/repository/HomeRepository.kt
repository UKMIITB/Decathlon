package com.example.decathlon.homescreen.repository

import com.example.decathlon.BuildConfig
import com.example.decathlon.common.DecathlonRetrofitService
import com.example.decathlon.homescreen.model.network.HomeItemsRequest
import com.example.decathlon.homescreen.model.network.HomeItemsResponse
import com.example.decathlon.util.MockUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class HomeRepository @Inject constructor(
    private val decathlonRetrofitService: DecathlonRetrofitService
) {

    suspend fun getHomeData(homeItemsRequest: HomeItemsRequest): Response<HomeItemsResponse> =
        withContext(Dispatchers.IO) {

            delay(3.seconds)

            return@withContext if (BuildConfig.IS_MOCK_ENABLED) {
                MockUtil.getMockedHomeData(homeItemsRequest = homeItemsRequest)
            } else {
                decathlonRetrofitService.getHomeData(homeItemsRequest = homeItemsRequest)
            }
        }
}
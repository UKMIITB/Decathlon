package com.example.decathlon.util

import com.example.decathlon.homescreen.model.network.HomeItem
import com.example.decathlon.homescreen.model.network.HomeItemsRequest
import com.example.decathlon.homescreen.model.network.HomeItemsResponse
import com.example.decathlon.homescreen.model.view.HomeScreenSortOptions
import retrofit2.Response
import java.util.UUID

object MockUtil {

    private val brandNames = listOf(
        "Nike",
        "Adidas",
        "Reebok",
        "Puma",
        "New Balance",
        "Vans",
        "Converse",
        "Skechers",
        "Timberland",
        "Dr. Martens",
        "ASICS",
        "Fila",
        "Under Armour",
        "Clarks",
        "ECCO"
    )

    private fun getRandomUUID() = UUID.randomUUID().toString()

    private fun getRandomName() = (1..7).map {
        (('A'..'Z') + ('a'..'z')).random()
    }.joinToString(separator = "")

    private fun getRandomPrice() = (1..10000).random().toFloat()

    private fun getRandomBrandName() = brandNames.random()

    private val imageUrls = listOf(
        "https://raw.githubusercontent.com/UKMIITB/Decathlon/770ae6ddba12493a7d61cf41cbb7da27f9cbb22b/storage/shoe_1.jpg",
        "https://raw.githubusercontent.com/UKMIITB/Decathlon/770ae6ddba12493a7d61cf41cbb7da27f9cbb22b/storage/shoe_2.jpg",
        "https://raw.githubusercontent.com/UKMIITB/Decathlon/770ae6ddba12493a7d61cf41cbb7da27f9cbb22b/storage/shoe_3.jpg",
        "https://raw.githubusercontent.com/UKMIITB/Decathlon/770ae6ddba12493a7d61cf41cbb7da27f9cbb22b/storage/shoe_4.jpg",
        "https://raw.githubusercontent.com/UKMIITB/Decathlon/770ae6ddba12493a7d61cf41cbb7da27f9cbb22b/storage/shoe_5.jpg",
        "https://raw.githubusercontent.com/UKMIITB/Decathlon/770ae6ddba12493a7d61cf41cbb7da27f9cbb22b/storage/shoe_6.jpg",
        "https://raw.githubusercontent.com/UKMIITB/Decathlon/770ae6ddba12493a7d61cf41cbb7da27f9cbb22b/storage/shoe_7.jpg",
        "https://raw.githubusercontent.com/UKMIITB/Decathlon/770ae6ddba12493a7d61cf41cbb7da27f9cbb22b/storage/shoe_8.jpg"
    )

    private fun getImageUrl() = imageUrls.random()

    private val homeItemsDummyList = (1..100).map {
        HomeItem(
            id = getRandomUUID(),
            name = getRandomName(),
            price = getRandomPrice(),
            imageUrl = getImageUrl(),
            brand = getRandomBrandName()
        )
    }

    fun getMockedHomeData(homeItemsRequest: HomeItemsRequest): Response<HomeItemsResponse> {
        val queryFilteredHomeItems = homeItemsDummyList.filter {
            it.name.contains(homeItemsRequest.query, ignoreCase = true)
                    || it.brand.contains(homeItemsRequest.query, ignoreCase = true)
        }

        val sortedHomeItems =
            when (HomeScreenSortOptions.getHomeScreenSortOptionFromValue(homeItemsRequest.sort)) {
                HomeScreenSortOptions.MOST_RELEVANT -> queryFilteredHomeItems
                HomeScreenSortOptions.PRICE_ASCENDING -> queryFilteredHomeItems.sortedBy { it.price }
                HomeScreenSortOptions.PRICE_DESCENDING -> queryFilteredHomeItems.sortedByDescending { it.price }
                HomeScreenSortOptions.NAME_ASCENDING -> queryFilteredHomeItems.sortedBy { it.name }
                HomeScreenSortOptions.NAME_DESCENDING -> queryFilteredHomeItems.sortedByDescending { it.name }
            }

        return Response.success(
            HomeItemsResponse(
                items = sortedHomeItems
                    .drop(homeItemsRequest.pageNumber * homeItemsRequest.pageSize)
                    .take(homeItemsRequest.pageSize)
            )
        )
    }
}
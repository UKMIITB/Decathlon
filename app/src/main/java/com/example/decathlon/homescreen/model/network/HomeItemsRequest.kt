package com.example.decathlon.homescreen.model.network

data class HomeItemsRequest(
    val pageSize: Int,
    val pageNumber: Int,
    val query: String,
    val sort: String
)

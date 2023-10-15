package com.example.decathlon.homescreen.model.view

enum class HomeScreenSortOptions(val value: String) {
    MOST_RELEVANT("Most Relevant"),
    PRICE_ASCENDING("Price: Low to High"),
    PRICE_DESCENDING("Price: High to Low"),
    NAME_ASCENDING("Name: A to Z"),
    NAME_DESCENDING("Name: Z to A");

    companion object {
        fun getHomeScreenSortOptionFromValue(value: String): HomeScreenSortOptions {
            return when (value) {
                MOST_RELEVANT.value -> MOST_RELEVANT
                PRICE_ASCENDING.value -> PRICE_ASCENDING
                PRICE_DESCENDING.value -> PRICE_DESCENDING
                NAME_ASCENDING.value -> NAME_ASCENDING
                NAME_DESCENDING.value -> NAME_DESCENDING
                else -> MOST_RELEVANT
            }
        }
    }
}
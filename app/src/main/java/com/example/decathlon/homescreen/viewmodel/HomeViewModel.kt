package com.example.decathlon.homescreen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.decathlon.homescreen.model.HomeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val homeItem1 = HomeItem(
        name = "Domyos",
        id = "123",
        price = 1000f,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        brand = "Decathlon"
    )

    val homeItem2 = HomeItem(
        name = "Domyos2",
        id = "123",
        price = 2000f,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        brand = "Decathlon"
    )
}
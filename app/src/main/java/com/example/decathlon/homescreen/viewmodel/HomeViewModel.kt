package com.example.decathlon.homescreen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.decathlon.homescreen.model.HomeItem
import com.example.decathlon.homescreen.model.HomeScreenBottomSheetHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _bottomSheetUIState =
        MutableStateFlow(HomeScreenBottomSheetHolder(showBottomSheet = false))
    val bottomSheetUIState = _bottomSheetUIState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun updateBottomSheetUIState(showBottomSheet: Boolean) {
        _bottomSheetUIState.update {
            it.copy(showBottomSheet = showBottomSheet)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

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
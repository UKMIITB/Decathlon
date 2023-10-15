package com.example.decathlon.homescreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.decathlon.homescreen.model.network.HomeItem
import com.example.decathlon.homescreen.model.view.HomeScreenBottomSheetHolder
import com.example.decathlon.homescreen.model.view.HomeScreenSortOptions
import com.example.decathlon.homescreen.repository.HomeRepository
import com.example.decathlon.homescreen.repository.HomeScreenItemSource
import com.example.decathlon.homescreen.repository.HomeScreenItemSource.Companion.HOME_SCREEN_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val SEARCH_QUERY_DEBOUNCE_TIME = 300.milliseconds

    private val _bottomSheetUIState =
        MutableStateFlow(HomeScreenBottomSheetHolder(showBottomSheet = false))
    val bottomSheetUIState = _bottomSheetUIState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _activeSort = MutableStateFlow(HomeScreenSortOptions.MOST_RELEVANT)
    val activeSort = _activeSort.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val homeScreenItemsPager =
        combine(
            searchQuery.debounce(SEARCH_QUERY_DEBOUNCE_TIME).distinctUntilChanged(),
            activeSort
        ) { query, sort ->
            query to sort
        }.flatMapLatest {
            Pager(
                config = PagingConfig(
                    pageSize = HOME_SCREEN_PAGE_SIZE,
                    prefetchDistance = HOME_SCREEN_PAGE_SIZE
                )
            ) {
                HomeScreenItemSource(
                    query = searchQuery.value,
                    sort = activeSort.value.value,
                    homeRepository = homeRepository
                )
            }.flow.cachedIn(scope = viewModelScope)
        }

    fun updateBottomSheetUIState(showBottomSheet: Boolean) {
        _bottomSheetUIState.update {
            it.copy(showBottomSheet = showBottomSheet)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    fun updateActiveSort(activeSort: HomeScreenSortOptions) {
        _activeSort.update { activeSort }
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
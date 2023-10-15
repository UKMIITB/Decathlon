package com.example.decathlon.homescreen.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.decathlon.homescreen.model.network.HomeItem
import com.example.decathlon.homescreen.model.network.HomeItemsRequest
import javax.inject.Inject

class HomeScreenItemSource @Inject constructor(
    private val query: String,
    private val sort: String,
    private val homeRepository: HomeRepository
) : PagingSource<Int, HomeItem>() {

    companion object {
        const val HOME_SCREEN_PAGE_SIZE = 20
        const val PAGINATION_DEFAULT_KEY_VALUE = 0
    }

    override fun getRefreshKey(state: PagingState<Int, HomeItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeItem> {
        try {
            val currentPage = params.key ?: PAGINATION_DEFAULT_KEY_VALUE

            val homeItemsAPIResponse = homeRepository.getHomeData(
                homeItemsRequest = HomeItemsRequest(
                    pageNumber = currentPage,
                    pageSize = HOME_SCREEN_PAGE_SIZE,
                    query = query,
                    sort = sort
                )
            )

            if (!homeItemsAPIResponse.isSuccessful) {
                return LoadResult.Error(Exception("Error fetching home items"))
            }

            val homeItems = homeItemsAPIResponse.body()?.items ?: emptyList()

            // If list size is equal to max page size, then next page might exist
            val isNextPageExist = homeItems.isNotEmpty() && homeItems.size == HOME_SCREEN_PAGE_SIZE

            return LoadResult.Page(
                data = homeItems,
                prevKey = if (currentPage == PAGINATION_DEFAULT_KEY_VALUE) null else currentPage - 1,
                nextKey = if (isNextPageExist) currentPage + 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
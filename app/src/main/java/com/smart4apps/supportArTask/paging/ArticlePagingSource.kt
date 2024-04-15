package com.smart4apps.supportArTask.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smart4apps.supportArTask.data.model.Article

class ArticlePagingSource(private val articles: List<Article>) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPageNumber = params.key ?: 0 // Start loading from the first page (key is null for initial load)
            val itemsPerPage = params.loadSize

            // Calculate the range of items to load for the current page
            val startIndex = nextPageNumber * itemsPerPage
            val endIndex = startIndex + itemsPerPage

            // Slice the list to get the current page of items
            val currentPageItems = articles.subList(startIndex, endIndex.coerceAtMost(articles.size))

            // Determine if there's a next page
            val hasNextPage = endIndex < articles.size

            // Prepare the LoadResult with the current page data and next page key
            LoadResult.Page(
                data = currentPageItems,
                prevKey = null, // Assuming no previous key (we're not supporting paging backwards)
                nextKey = if (hasNextPage) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
}

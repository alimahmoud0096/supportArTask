package com.smart4apps.supportArTask.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smart4apps.supportArTask.data.api.ApiService
import com.smart4apps.supportArTask.data.model.Article
import com.smart4apps.supportArTask.data.room.ArticlesDao
import com.smart4apps.supportArTask.ui.viewmodel.PAGE_SIZE

class NewsDataSource(
    private val q: String?, private val apiService: ApiService,private val  articlesDao: ArticlesDao
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: 1
            val response = apiService.listNews(q, position, PAGE_SIZE)


            if (response.status == "ok") {
                val articles = response.articles?.map { article ->
                    val isFav = articlesDao.getArticle(article.publishedAt) != null
                    article.copy(isFav = isFav) // Create a copy of the article with updated 'isFav' property
                } ?: emptyList()
                LoadResult.Page(
                    data = articles,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = if (response.articles?.isEmpty() == true) null else (position + 1)
                )
            } else {
                LoadResult.Error(throw Exception("No Response"))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
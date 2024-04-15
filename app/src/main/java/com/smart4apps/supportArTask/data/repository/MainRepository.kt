package com.smart4apps.supportArTask.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.smart4apps.supportArTask.data.api.ApiService
import com.smart4apps.supportArTask.data.model.Article
import com.smart4apps.supportArTask.data.room.ArticlesDao
import com.smart4apps.supportArTask.paging.NewsDataSource
import com.smart4apps.supportArTask.ui.viewmodel.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val articlesDao: ArticlesDao,
) {


    fun listNews(q:String?): LiveData<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, maxSize = 200),
        pagingSourceFactory = { NewsDataSource(q,apiService,articlesDao) }
    ).liveData


    fun getAllFavArticles(): Flow<List<Article>> {
        return articlesDao.getAllArticles()
    }

    suspend fun insertCompetitions(articles: List<Article>) {
        articlesDao.insertArticles(articles)
    }

    suspend fun removeArticle(article: Article) {
        articlesDao.removeArticle(article)
    }

   suspend fun insertArticle(article: Article) {
        articlesDao.insertArticle(article)
    }


}
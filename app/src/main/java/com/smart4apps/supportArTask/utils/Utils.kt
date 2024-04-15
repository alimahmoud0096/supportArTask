package com.smart4apps.supportArTask.utils

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smart4apps.supportArTask.data.model.Article
import com.smart4apps.supportArTask.paging.ArticlePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class Utils {



    companion object {

        fun createPagingData(articles: List<Article>): Flow<PagingData<Article>> {
            return Pager(
                config = PagingConfig(pageSize = 10), // Adjust the page size as needed
                pagingSourceFactory = { ArticlePagingSource(articles) }
            ).flow
        }
    }
}
package com.smart4apps.supportArTask.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smart4apps.supportArTask.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {


    @Query("SELECT * FROM Article")
    fun getAllArticles(): Flow<List<Article>>
    @Query("SELECT * FROM Article where publishedAt=:createdAt")
    suspend fun getArticle(createdAt:String?): Article?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)
    @Delete
   suspend fun removeArticle(article: Article)
}
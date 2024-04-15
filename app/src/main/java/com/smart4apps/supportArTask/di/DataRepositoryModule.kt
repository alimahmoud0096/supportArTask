package com.smart4apps.supportArTask.di

import android.content.Context
import androidx.room.Room
import com.smart4apps.supportArTask.data.api.ApiService
import com.smart4apps.supportArTask.data.model.Article
import com.smart4apps.supportArTask.data.repository.MainRepository
import com.smart4apps.supportArTask.data.room.ArticlesDao
import com.smart4apps.supportArTask.data.room.ArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataRepository(apiService: ApiService,articlesDao: ArticlesDao): MainRepository {
        return MainRepository(apiService,articlesDao)
    }


    @Provides
    fun provideArticlesDao(appDatabase: ArticlesDatabase): ArticlesDao {
        return appDatabase.articlesDao()
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ArticlesDatabase {


        return Room.databaseBuilder(
            appContext,
            ArticlesDatabase::class.java,
            "ArticlesDatabase.db"
        ).build()

    }


}
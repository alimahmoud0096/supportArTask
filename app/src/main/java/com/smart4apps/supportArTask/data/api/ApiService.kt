package com.smart4apps.supportArTask.data.api

import com.smart4apps.supportArTask.BuildConfig
import com.smart4apps.supportArTask.data.model.NewsRes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("everything")
    suspend fun listNews(
        @Query("q") q: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = BuildConfig.api_key
    ): NewsRes
}
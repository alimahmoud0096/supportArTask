package com.smart4apps.supportArTask.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class NewsRes(
    @SerializedName("status") val status: String? = null,
    @SerializedName("totalResults") val totalResults: Long? = null,
    @SerializedName("articles") val articles: List<Article>? = null,
)

@Entity("Article")
@Parcelize
data class Article(
     @SerializedName("author") val author: String?,
    @SerializedName("source") val source: SourceItem? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
     @PrimaryKey  @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String? = null,
    var isFav: Boolean= false,
//    @PrimaryKey(true) val id: Int? =null,
):Parcelable

@Parcelize
data class SourceItem(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
):Parcelable


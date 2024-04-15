package com.smart4apps.supportArTask.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.smart4apps.supportArTask.data.model.Article
import com.smart4apps.supportArTask.data.repository.MainRepository
import com.smart4apps.supportArTask.paging.NewsDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE=10
@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {


    var articlesListLiv: MutableStateFlow<List<Article>> =
        MutableStateFlow(arrayListOf())


    // Define a MutableLiveData to hold the query value
    val queryLiveData = MutableLiveData<String>("")


    // Initialize newsList using queryLiveData for dynamic updates
    val newsList: LiveData<PagingData<Article>> = queryLiveData.switchMap { query ->
        mainRepository.listNews(query)
            .cachedIn(viewModelScope)
    }




    fun getAllFavArticles() {
        viewModelScope.launch {
            //get data from local storage
            mainRepository.getAllFavArticles().collectLatest {
                Log.d("TAG", "getAllFavArticles:${it.size} ")
                articlesListLiv.emit(it)
            }
        }
    }
    fun insertArticle(article: Article) {
        viewModelScope.launch {
            //get data from local storage
            mainRepository.insertArticle(article)
        }
    }
    fun removeArticle(article: Article) {
        viewModelScope.launch {
            //get data from local storage
            mainRepository.removeArticle(article)
        }
    }



}
package com.example.dharmav10.home

import android.app.Application
import androidx.lifecycle.*
import com.example.dharmav10.database.getDatabase
import com.example.dharmav10.domain.NewsProperty
import com.example.dharmav10.repo.NewsRepository
import com.example.dharmav10.utils.Constants
import kotlinx.coroutines.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {


    private val _navigateToDetailNews = MutableLiveData<NewsProperty>()
    val navigateToDetailNews: LiveData<NewsProperty> get() = _navigateToDetailNews

    private val viewModelJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    //Get Data From Room Database
    private val database = getDatabase(application)
    private val newsRepository = NewsRepository(application,database)

    init {
        coroutineScope.launch {
            newsRepository.addToRoom(Constants.KALMYK_URL,"kalmyk")
            newsRepository.addToRoom(Constants.WORLD_URL,"world")
            newsRepository.addToRoom(Constants.RUSSIA_URL,"russia")
            newsRepository.addToRoom(Constants.ENTERTAINMENT_URL,"entertainment")
            newsRepository.addToRoom(Constants.ECONOMY_URL,"economy")
            newsRepository.addToRoom(Constants.SPORTS_URL,"sports")
            newsRepository.addToRoom(Constants.DHARMA_URL,"dharma")
        }
    }

    val listNewsAll = newsRepository.newsAll
    val listNewsKalmyk = newsRepository.newsKalmyk
    val listNewsWorld = newsRepository.newsWorld
    val listNewsRussia = newsRepository.newsRussia
    val listNewsEntertainment = newsRepository.newsEntertainment
    val listNewsEconomy = newsRepository.newsEconomy
    val listNewsSports = newsRepository.newsSports
    val listNewsDharma = newsRepository.newsDharma

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onItemSelected(newsProperty: NewsProperty) {
        _navigateToDetailNews.value = newsProperty
    }

    //After finish to navigate
    fun onFinishItemSelected() {
        _navigateToDetailNews.value = null
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
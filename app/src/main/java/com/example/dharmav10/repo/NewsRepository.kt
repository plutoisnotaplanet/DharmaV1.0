package com.example.dharmav10.repo

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.dharmav10.database.*
import com.example.dharmav10.database.MyArticle.Companion.toDateFromRSS
import com.example.dharmav10.domain.NewsProperty
import com.example.dharmav10.network.NetworkState
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception
import java.util.*

class NewsRepository(
    private val context: Context,
    private val database: NewsDatabase
) {

    companion object {
        const val cacheExpirationMillis = 1000L * 60L * 60L * 24 //1 day
    }

    val state = MutableLiveData<NetworkState>()

    private val parser by lazy {
        Parser.Builder()
            .context(context)
            .cacheExpirationMillis(cacheExpirationMillis)
            .build()
    }

    //Variabel to save LiveData<List<NewsProperty>>
    //Main Data
    val newsAll: LiveData<List<NewsProperty>> = Transformations.map(
        database.newsDao.getAllNews()
    ) {
        it.asDomainModel()
    }

    val newsRussia : LiveData<List<NewsProperty>> = Transformations.map(
        database.newsDao.getCategory("russia")
    ){
        it.asDomainModel()
    }

    val newsKalmyk : LiveData<List<NewsProperty>> = Transformations.map(
        database.newsDao.getCategory("kalmyk")
    ){
        it.asDomainModel()
    }

    val newsSports : LiveData<List<NewsProperty>> = Transformations.map(
        database.newsDao.getCategory("sports")
    ){
        it.asDomainModel()
    }
    val newsWorld : LiveData<List<NewsProperty>> = Transformations.map(
        database.newsDao.getCategory("world")
    ){
        it.asDomainModel()
    }
    val newsEconomy : LiveData<List<NewsProperty>> = Transformations.map(
        database.newsDao.getCategory("economy")
    ){
        it.asDomainModel()
    }
    val newsEntertainment : LiveData<List<NewsProperty>> = Transformations.map(
        database.newsDao.getCategory("entertainment")
    ){
        it.asDomainModel()
    }
    val newsDharma : LiveData<List<NewsProperty>> = Transformations.map(
        database.newsDao.getCategory("dharma")
    ){
        it.asDomainModel()
    }

    fun addToRoom(
        url: String,
        category: String) {
        GlobalScope.launch {
            state.postValue(NetworkState.LOADING)
            val startTime = System.currentTimeMillis()

            val allArticlesList = mutableListOf<MyArticle>()
            Timber.i("Starting download files from $url")
            async (Dispatchers.IO) {
                loadArticlesFromUrl(url,allArticlesList)
                allArticlesList.forEach {
                    it.sourceName = category
                    Timber.i("Category is ${it.sourceName}")
                }
            }.await()

            for (article in allArticlesList) {
                Timber.i("Before exists")
                article.guid?.let { guid ->
                    Timber.i("$guid is ok")
                    article.pubDate?.let { pubDate ->
                        Timber.i("$pubDate")
                        if (!database.newsDao.existsByGuidAndDate(guid, pubDate.toDateFromRSS() ?: Date())) {
                            Timber.i("Im here exists is ok")
                            val newEntity = DatabaseNews.fromMyArticle(article)
                            Timber.i("Entity is ready")
                            database.newsDao.insert(newEntity)
                        }
                    }
                }
            }
            val duration = System.currentTimeMillis() - startTime

            Timber.i("Repository: i finished in $duration ms")
        }
    }

    private suspend fun loadArticlesFromUrl(
        url: String,
        result: MutableList<MyArticle>
    ) {
        try {
            parser.flushCache(url)
            val articles = parser.getChannel(url).articles
            synchronized(this) {
                result.addAll(articles.map {
                    MyArticle.fromLibrary(it)
                }.filter { it.isValid }
                )
                Timber.i("result's size is ${result.size}")
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}
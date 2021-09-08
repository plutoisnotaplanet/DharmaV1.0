package com.example.dharmav10.database

import com.prof.rssparser.Article
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class MyArticle(
    var guid: String? = null,
    var title: String? = null,
    var author: String? = null,
    var link: String? = null,
    var pubDate: String? = null,
    var description: String? = null,
    var content: String? = null,
    var image: String? = null,
    var audio: String? = null,
    var video: String? = null,
    var sourceName: String? = null,
    var sourceUrl: String? = null,
    private var _categories: MutableList<String> = mutableListOf()
) : Serializable {

    val isValid: Boolean
        get() = !guid.isNullOrEmpty() && pubDate != null

    val formattedDate: Date?
        get() = pubDate?.toDateFromRSS()

    companion object {
        fun fromLibrary(article: Article): MyArticle {
            return MyArticle(
                guid = article.guid,
                title = article.title,
                author = article.author,
                link = article.link,
                pubDate = article.pubDate,
                description = article.description,
                content = article.content,
                image = article.image,
                audio = article.audio,
                video = article.video,
                sourceName = article.sourceName,
                sourceUrl = article.sourceUrl,
            )
        }

        fun String.toDateFromRSS(): Date? {
            return parseDate("EEE, dd MMM yyyy HH:mm:ss zzz")
        }

        fun String.parseDate(pattern: String = "yyyy-MM-dd'T'HH:mm"): Date? {
            return try {
                SimpleDateFormat(pattern, Locale.ENGLISH).parse(this)
            } catch (e: Exception) {
                //todo Timber
                null
            }
        }

    }

    val categories: MutableList<String>
        get() = _categories

    fun addCategory(category: String) {
        _categories.add(category)
    }
}


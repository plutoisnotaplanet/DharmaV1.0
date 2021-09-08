package com.example.dharmav10.domain

import android.os.Parcelable
import com.example.dharmav10.database.DatabaseNews
import com.prof.rssparser.Article
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsProperty>
)

@Parcelize
data class NewsProperty(
    var guid: String,
    var title: String?,
    var author: String?,
    var link: String?,
    var pubDate: String,
    var description: String?,
    var content: String?,
    var image: String?,
    var category: String?,
) : Parcelable


//Convert NewsProperty to DatabaseNews
fun NewsProperty.asDatabaseModel(): DatabaseNews {
    return DatabaseNews(
        guid = this.guid?: "",
        author = this.author,
        title = this.title!!,
        description = this.description,
        link = this.link,
        image = this.image,
        pubDate = this.pubDate,
        content = this.content,
        category = this.category
    )
}

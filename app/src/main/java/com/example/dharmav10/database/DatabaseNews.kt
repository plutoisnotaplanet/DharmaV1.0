package com.example.dharmav10.database

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.dharmav10.domain.NewsProperty
import com.prof.rssparser.Article
import java.io.Serializable

@Entity
class DatabaseNews constructor(
    @PrimaryKey
    var guid: String,
    var title: String,
    var author: String?,
    var link: String?,
    var pubDate: String,
    var description: String?,
    var content: String?,
    var image: String?,
    var category: String?,
) {
    companion object {
        fun fromMyArticle(article: MyArticle): DatabaseNews {
            return DatabaseNews(
                guid = article.guid ?: "",
                title = article.title?: "",
                image = article.image,
                author = article.author,
                category = article.sourceName,
                description = article.description.toString(),
                pubDate = article.pubDate?: "",
                link = article.link,
                content = article.content
            )
        }
    }

}


fun List<DatabaseNews>.asDomainModel(): List<NewsProperty> {
    return map {
        NewsProperty(
            guid = it.guid,
            link = it.link,
            author = it.author,
            title = it.title,
            description = it.description,
            image = it.image,
            pubDate = it.pubDate,
            content = it.content,
            category = it.category
        )
    }
}

fun Article.asDatabaseNews(): DatabaseNews {
    return DatabaseNews(
        guid = this.guid?: "",
            title = this.title!!,
            author = this.author,
            link = this.link,
            pubDate = this.pubDate?:"",
            description = this.description,
            content = this.content,
            image = this.image,
            category = this.sourceName
        )
}




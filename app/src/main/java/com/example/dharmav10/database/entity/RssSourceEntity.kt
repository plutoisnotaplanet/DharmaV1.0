package com.example.dharmav10.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import timber.log.Timber


@Entity(
    indices = [
        Index(value = ["url"])
    ]
)
data class RssSourceEntity(
    @PrimaryKey
    var url: String,

    var title: String? = null,
    var description: String? = null,
    var homePage: String? = null,
    var contactUrl: String? = null,
    var imageUrl: String? = null,
    var isUserAdded: Boolean = false,
    var isSelected: Boolean = false,
    var isHidden: Boolean = false,
    var forceOpenExternally: Boolean = false

) {
    override fun equals(other: Any?): Boolean {
        Timber.i("RSSSourceEntity: comparing ${this.title} with $other")
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RssSourceEntity

        if (url != other.url) return false
        if (title != other.title) return false
        if (imageUrl != other.imageUrl) return false
        if (isUserAdded != other.isUserAdded) return false
        if (isSelected != other.isSelected) return false
        if (isHidden != other.isHidden) return false
        if (description != other.description) return false

        return true
    }
}
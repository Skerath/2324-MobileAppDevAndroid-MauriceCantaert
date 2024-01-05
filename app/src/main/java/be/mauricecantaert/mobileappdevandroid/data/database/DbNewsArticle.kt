package be.mauricecantaert.mobileappdevandroid.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.model.NewsArticleText

@Entity(tableName = "article")
data class DbNewsArticle(
    @PrimaryKey val id: Long,
    val title: String,
    val articleUrl: String,
    val imageUrl: String,
    val author: String,
    val summary: String,
    val publishedAt: String,
    val isFavorited: Boolean,
    val articleText: String,
)

fun DbNewsArticle.asDomainObject(): Pair<NewsArticle, NewsArticleText> =
    NewsArticle(
        id = id,
        title = title,
        articleUrl = articleUrl,
        imageUrl = imageUrl,
        author = author,
        summary = summary,
        publishedAt = publishedAt,
        isFavorited = isFavorited,
    ) to NewsArticleText(articleText = articleText)

fun List<DbNewsArticle>.asDomainObjects() = map { it.asDomainObject() }

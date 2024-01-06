package be.mauricecantaert.mobileappdevandroid.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.model.NewsArticleText

/**
 * Database entity representing a news article stored locally.
 *
 * @param id Unique identifier for the news article.
 * @param title Title of the news article.
 * @param articleUrl URL of the news article.
 * @param imageUrl URL of the image associated with the news article.
 * @param author Author of the news article.
 * @param summary Summary or brief description of the news article.
 * @param publishedAt Date and time when the news article was published.
 * @param isFavorited Indicates whether the article is favorited or not.
 * @param articleText Full text content of the news article.
 */
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

/**
 * Converts the [DbNewsArticle] database object into a pair of [NewsArticle] and its belonging [NewsArticleText].
 *
 * @return A pair containing a [NewsArticle] representing the article's metadata and a [NewsArticleText] containing the article's full text content.
 */
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

/**
 * Converts a list of [DbNewsArticle] objects into a list of pairs of [NewsArticle] and belonging [NewsArticleText].
 *
 * @receiver The list of [DbNewsArticle] objects to be converted.
 * @return A list of pairs, each containing a [NewsArticle] representing an article's metadata and a [NewsArticleText] containing the article's full text content.
 */
fun List<DbNewsArticle>.asDomainObjects() = map { it.asDomainObject() }

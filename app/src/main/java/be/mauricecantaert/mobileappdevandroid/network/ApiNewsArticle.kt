package be.mauricecantaert.mobileappdevandroid.network

import be.mauricecantaert.mobileappdevandroid.model.NavigationDetails
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Serializable data class representing the API response for news articles.
 * @property count The total count of news articles.
 * @property next The URL to fetch the next articles in a paginated manner.
 * @property previous The URL to fetch the next articles in a paginated manner.
 * @property results The list of API news articles.
 */
@Serializable
data class ApiNewsArticlesResponse(
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<ApiNewsArticle>,
)

/**
 * Extension function converting the API news articles response to a domain object.
 * @return A [Pair] of [NavigationDetails] and a [List] of [NewsArticle].
 */
fun ApiNewsArticlesResponse.asDomainObject(): Pair<NavigationDetails, List<NewsArticle>> =
    NavigationDetails(
        next = next,
        previous = previous,
    ) to results.map {
        NewsArticle(
            id = it.id,
            title = it.title,
            articleUrl = it.url,
            imageUrl = it.imageUrl,
            author = it.newsSite,
            summary = it.summary,
            publishedAt = it.publishedAt,
        )
    }

/**
 * Serializable data class representing an API news article.
 * @property id The unique identifier of the news article.
 * @property title The title of the news article.
 * @property url The URL of the news article.
 * @property imageUrl The URL of the news article thumbnail.
 * @property newsSite The news site / author providing the article.
 * @property summary The summary of the news article.
 * @property publishedAt The publication date of the news article.
 */
@Serializable
data class ApiNewsArticle(
    val id: Long,
    val title: String,
    val url: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("news_site") val newsSite: String,
    val summary: String,
    @SerialName("published_at") val publishedAt: String,
    @Transient
    @SerialName("updated_at")
    val updatedAt: String = "",
    @Transient val featured: Boolean = false,
    @Transient val launches: List<Any> = listOf(),
    @Transient val events: List<Any> = listOf(),
)

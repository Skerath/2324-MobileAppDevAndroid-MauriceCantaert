package be.mauricecantaert.mobileappdevandroid.network

import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ApiNewsArticlesResponse(
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<ApiNewsArticle>,
)

fun ApiNewsArticlesResponse.asDomainObjects(): List<NewsArticle> = results.map {
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
    val featured: Boolean,
    @Transient val launches: List<Launch> = listOf(),
    @Transient val events: List<Event> = listOf(),
)

@Serializable
data class Launch(
    @SerialName("launch_id") val launchId: String,
    val provider: String,
)

@Serializable
data class Event(
    @SerialName("event_id") val eventId: Long,
    val provider: String,
)

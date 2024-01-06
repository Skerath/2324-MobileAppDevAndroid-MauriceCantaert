package be.mauricecantaert.mobileappdevandroid.model

import be.mauricecantaert.mobileappdevandroid.data.database.DbNewsArticle
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState.Error
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState.Loading
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState.Success

/**
 * Represents the navigation details for news articles.
 * @property next The URL for the next page of articles.
 * @property previous The URL for the previous page of articles.
 */
data class NavigationDetails(
    val next: String? = null,
    val previous: String? = null,
)

/**
 * Represents a news article.
 * @property id The unique identifier of the article.
 * @property title The title of the article.
 * @property articleUrl The URL of the article.
 * @property imageUrl The URL of the thumbnail associated with the article.
 * @property author The author of the article.
 * @property summary The summary or description of the article.
 * @property publishedAt The publication date of the article.
 * @property isFavorited Indicates whether the article is favorited by the user (default is `false`).
 */
data class NewsArticle(
    val id: Long,
    val title: String,
    val articleUrl: String,
    val imageUrl: String,
    val author: String,
    val summary: String,
    val publishedAt: String,
    val isFavorited: Boolean = false,
)

/**
 * Converts a [NewsArticle] to its database representation [DbNewsArticle].
 * @param articleText The textual content of the full article, as scraped from [NewsArticle.articleUrl]
 * @return The [DbNewsArticle] representation of the [NewsArticle] including the scraped article content.
 */
fun NewsArticle.asDbArticle(articleText: String): DbNewsArticle =
    DbNewsArticle(
        id = id,
        title = title,
        articleUrl = articleUrl,
        imageUrl = imageUrl,
        author = author,
        summary = summary,
        publishedAt = publishedAt,
        isFavorited = true,
        articleText = articleText,
    )

/**
 * Represents the scraped content of a news article for offline access.
 * @property articleText The textual content of the full article.
 */
data class NewsArticleText(
    val articleText: String = "",
)

/**
 * Represents the different states of API calls for fetching news articles.
 * [Success]: Indicates a successful API call.
 * [Error]: Indicates something went wrong, contains an [ErrorType] for the UI to represent.
 * [Loading]: Indicates that the API call is in progress.
 */
sealed interface NewsArticlesApiState {
    data object Success : NewsArticlesApiState
    data class Error(val errorType: ErrorType) : NewsArticlesApiState
    data object Loading : NewsArticlesApiState
}

/**
 * Represents the state of news articles in the application.
 * @property navigationDetails The navigation details for the list of articles.
 * @property newsArticles The list of news articles.
 */
data class NewsArticleListState(
    val navigationDetails: NavigationDetails = NavigationDetails(),
    val newsArticles: List<NewsArticle> = listOf(),
)

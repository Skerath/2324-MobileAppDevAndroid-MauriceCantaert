package be.mauricecantaert.mobileappdevandroid.model

import be.mauricecantaert.mobileappdevandroid.data.database.DbNewsArticle

data class NavigationDetails(
    val next: String? = null,
    val previous: String? = null,
)

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

data class NewsArticleText(
    val articleText: String = "",
)

sealed interface NewsArticlesApiState {
    data object Success : NewsArticlesApiState
    data class Error(val errorType: ErrorType) : NewsArticlesApiState
    data object Loading : NewsArticlesApiState
}

data class NewsArticleListState(
    val navigationDetails: NavigationDetails = NavigationDetails(),
    val newsArticles: List<NewsArticle> = listOf(),
)

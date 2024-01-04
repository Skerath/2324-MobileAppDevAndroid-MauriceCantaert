package be.mauricecantaert.mobileappdevandroid.model

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
)

sealed interface NewsArticlesApiState {
    data object Success : NewsArticlesApiState
    data class Error(val errorMessage: String) : NewsArticlesApiState
    data object Loading : NewsArticlesApiState
}

data class NewsArticleListState(
    val navigationDetails: NavigationDetails = NavigationDetails(),
    val newsArticles: List<NewsArticle> = listOf(),
)

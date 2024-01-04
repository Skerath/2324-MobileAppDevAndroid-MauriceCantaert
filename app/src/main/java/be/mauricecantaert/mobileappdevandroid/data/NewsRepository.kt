package be.mauricecantaert.mobileappdevandroid.data

import be.mauricecantaert.mobileappdevandroid.model.NavigationDetails
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.network.NewsApiService
import be.mauricecantaert.mobileappdevandroid.network.asDomainObject

interface NewsRepository {
    suspend fun getArticles(offset: Int): Pair<NavigationDetails, List<NewsArticle>>
}

class NewsApiRepository(
    private val newsApiService: NewsApiService,
) : NewsRepository {
    override suspend fun getArticles(offset: Int): Pair<NavigationDetails, List<NewsArticle>> =
        newsApiService.getArticles(offset).asDomainObject()
}

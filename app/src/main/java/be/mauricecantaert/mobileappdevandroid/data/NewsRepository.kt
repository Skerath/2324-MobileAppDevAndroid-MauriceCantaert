package be.mauricecantaert.mobileappdevandroid.data

import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.network.NewsApiService
import be.mauricecantaert.mobileappdevandroid.network.asDomainObjects

interface NewsRepository {
    suspend fun getArticles(): List<NewsArticle>
}

class NewsApiRepository(
    private val newsApiService: NewsApiService,
) : NewsRepository {
    override suspend fun getArticles(): List<NewsArticle> =
        newsApiService.getArticles().asDomainObjects()
}

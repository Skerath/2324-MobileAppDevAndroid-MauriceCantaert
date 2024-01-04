package be.mauricecantaert.mobileappdevandroid.data

import be.mauricecantaert.mobileappdevandroid.data.database.NewsDao
import be.mauricecantaert.mobileappdevandroid.data.database.asDomainObjects
import be.mauricecantaert.mobileappdevandroid.model.NavigationDetails
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.model.NewsArticleText
import be.mauricecantaert.mobileappdevandroid.model.asDbArticle
import be.mauricecantaert.mobileappdevandroid.network.NewsApiService
import be.mauricecantaert.mobileappdevandroid.network.asDomainObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface NewsRepository {
    suspend fun getArticles(offset: Int): Pair<NavigationDetails, List<NewsArticle>>
    suspend fun favoriteArticle(item: NewsArticle)
    suspend fun unfavoriteArticle(id: Long)
    suspend fun isFavorited(id: Long): Boolean
    fun getFavoritedArticles(): Flow<List<Pair<NewsArticle, NewsArticleText>>>
}

class NewsApiRepository(
    private val newsApiService: NewsApiService,
    private val newsDao: NewsDao,
) : NewsRepository {
    override suspend fun getArticles(offset: Int): Pair<NavigationDetails, List<NewsArticle>> {
        val result = newsApiService.getArticles(offset).asDomainObject()
        // check if results are favorited before returning
        return result.copy(
            second = result.second.map {
                it.copy(
                    isFavorited = newsDao.isFavorited(it.id),
                )
            },
        )
    }

    override suspend fun favoriteArticle(item: NewsArticle) {
        newsDao.insertNewsArticle(item.asDbArticle("Test"))
    }

    override suspend fun unfavoriteArticle(id: Long) {
        newsDao.removeNewsArticle(id)
    }

    override suspend fun isFavorited(id: Long): Boolean = newsDao.isFavorited(id)

    override fun getFavoritedArticles(): Flow<List<Pair<NewsArticle, NewsArticleText>>> =
        newsDao.getNewsArticles()
            .map {
                it.asDomainObjects()
            }
}

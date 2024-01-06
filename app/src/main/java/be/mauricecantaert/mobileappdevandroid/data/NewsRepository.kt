package be.mauricecantaert.mobileappdevandroid.data

import be.mauricecantaert.mobileappdevandroid.data.database.NewsDao
import be.mauricecantaert.mobileappdevandroid.data.database.asDomainObjects
import be.mauricecantaert.mobileappdevandroid.model.NavigationDetails
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.model.NewsArticleText
import be.mauricecantaert.mobileappdevandroid.model.asDbArticle
import be.mauricecantaert.mobileappdevandroid.network.NewsApiService
import be.mauricecantaert.mobileappdevandroid.network.WebService
import be.mauricecantaert.mobileappdevandroid.network.asDomainObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jsoup.Jsoup

/**
 * Interface defining the contract for interacting with news-related data.
 * It provides methods for fetching news articles, marking them as favorite and saving them for offline reading, and retrieving favorited articles.
 */
interface NewsRepository {
    /**
     * Fetches a list of news articles along with navigation details based on the given offset.
     *
     * @param offset The offset indicating the starting point for fetching articles.
     * @return A [Pair] containing the navigation details for the next fetch and a list of [NewsArticle].
     */
    suspend fun getArticles(offset: Int): Pair<NavigationDetails, List<NewsArticle>>

    /**
     * Marks a specific news article as a favorite for offline reading.
     * Saves the article locally along with the full article content, fetched from the [NewsArticle.articleUrl]
     *
     * @param item The [NewsArticle] to be marked as a favorite.
     */
    suspend fun favoriteArticle(item: NewsArticle)

    /**
     * Removes a favorited news article from offline storage.
     *
     * @param id The unique identifier of the article to be removed from favorites.
     */
    suspend fun unfavoriteArticle(id: Long)

    /**
     * Checks if a specific news article is marked as a favorite and stored locally.
     *
     * @param id The unique identifier of the article to check.
     * @return A boolean indicating whether the article is favorited or not.
     */
    suspend fun isFavorited(id: Long): Boolean

    /**
     * Retrieves a flow of favorited articles along with their text content.
     *
     * @return A [Flow] emitting a list of pairs containing [NewsArticle] and [NewsArticleText].
     */
    fun getFavoritedArticles(): Flow<List<Pair<NewsArticle, NewsArticleText>>>
}

/**
 * Implementation of [NewsRepository] that fetches news articles from an API and manages favoriting articles.
 *
 * @property newsApiService The service responsible for fetching news articles via API.
 * @property newsDao The data access object for managing news articles in the local database.
 * @property webService The service responsible for fetching HTML content from articles.
 */
class NewsApiRepository(
    private val newsApiService: NewsApiService,
    private val newsDao: NewsDao,
    private val webService: WebService,
) : NewsRepository {

    /**
     * Fetches a list of news articles along with navigation details based on the given offset.
     *
     * @param offset The offset indicating the starting point for fetching articles.
     * @return A [Pair] containing the navigation details for the next fetch and a list of [NewsArticle].
     */
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

    /**
     * Marks a specific news article as a favorite for offline reading.
     * Saves the article locally along with the full article content, fetched from the [NewsArticle.articleUrl]
     *
     * @param item The [NewsArticle] to be marked as a favorite.
     */
    override suspend fun favoriteArticle(item: NewsArticle) {
        // retrieve html text from website to read offline
        val articleHtml = webService.getHtmlFromArticle(item.articleUrl)
        val parsedArticleHtml = Jsoup.parse(articleHtml).body().text()
        // insert news article with parsedArticleHtml text as articleText
        newsDao.insertNewsArticle(item.asDbArticle(parsedArticleHtml))
    }

    /**
     * Removes a favorited news article from offline storage.
     *
     * @param id The unique identifier of the article to be removed from favorites.
     */
    override suspend fun unfavoriteArticle(id: Long) {
        newsDao.removeNewsArticle(id)
    }

    /**
     * Checks if a specific news article is marked as a favorite and stored locally.
     *
     * @param id The unique identifier of the article to check.
     * @return A boolean indicating whether the article is favorited or not.
     */
    override suspend fun isFavorited(id: Long): Boolean = newsDao.isFavorited(id)

    /**
     * Retrieves a flow of favorited articles along with their text content.
     *
     * @return A [Flow] emitting a list of pairs containing [NewsArticle] and [NewsArticleText].
     */
    override fun getFavoritedArticles(): Flow<List<Pair<NewsArticle, NewsArticleText>>> =
        newsDao.getNewsArticles()
            .map {
                it.asDomainObjects()
            }
}

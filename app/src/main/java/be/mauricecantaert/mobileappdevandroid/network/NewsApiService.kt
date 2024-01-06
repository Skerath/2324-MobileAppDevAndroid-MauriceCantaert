package be.mauricecantaert.mobileappdevandroid.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining the news API service.
 */
interface NewsApiService {
    /**
     * Fetches a list of articles from the API based on the provided offset.
     * @param offset The offset value indicating the starting point for fetching articles (default: 0).
     * @return An [ApiNewsArticlesResponse] containing the list of articles and navigation details.
     */
    @GET("articles")
    suspend fun getArticles(
        @Query("offset") offset: Int = 0,
    ): ApiNewsArticlesResponse
}

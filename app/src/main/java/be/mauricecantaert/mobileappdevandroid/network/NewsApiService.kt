package be.mauricecantaert.mobileappdevandroid.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("articles")
    suspend fun getArticles(
        @Query("offset") offset: Int = 0,
    ): ApiNewsArticlesResponse
}

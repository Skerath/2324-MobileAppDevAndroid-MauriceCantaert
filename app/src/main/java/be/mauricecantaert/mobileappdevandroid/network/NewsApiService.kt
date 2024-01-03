package be.mauricecantaert.mobileappdevandroid.network

import retrofit2.http.GET

interface NewsApiService {
    @GET("articles")
    suspend fun getArticles(): ApiNewsArticlesResponse
}

package be.mauricecantaert.mobileappdevandroid.network

import retrofit2.http.GET
import retrofit2.http.Url

interface WebService {

    @GET
    suspend fun getHtmlFromArticle(@Url url: String): String
}

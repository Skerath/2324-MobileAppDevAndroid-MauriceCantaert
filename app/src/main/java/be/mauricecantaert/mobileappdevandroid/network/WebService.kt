package be.mauricecantaert.mobileappdevandroid.network

import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Interface defining the web service to fetch HTML content used for scraping content for offline reading.
 */
interface WebService {

    /**
     * Fetches HTML content from the provided URL, used to scrape the content for offline reading.
     * @param url The URL of the article from which HTML content is to be fetched.
     * @return A [String] containing the HTML content fetched from the provided URL.
     */
    @GET
    suspend fun getHtmlFromArticle(@Url url: String): String
}

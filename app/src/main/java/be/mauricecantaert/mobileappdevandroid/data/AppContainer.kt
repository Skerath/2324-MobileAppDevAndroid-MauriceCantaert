package be.mauricecantaert.mobileappdevandroid.data

import be.mauricecantaert.mobileappdevandroid.network.NewsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

interface AppContainer {
    val newsRepository: NewsRepository
}

class DefaultAppContainer : AppContainer {
    private val restApiBaseUrl = "https://api.spaceflightnewsapi.net/v4/"

    private val json = Json { ignoreUnknownKeys = true }

    private val logger =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofitApi = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(restApiBaseUrl)
        .client(client)
        .build()

    private val newsApiRetrofitService: NewsApiService by lazy {
        retrofitApi.create(NewsApiService::class.java)
    }

    override val newsRepository: NewsRepository by lazy {
        NewsApiRepository(
            newsApiService = newsApiRetrofitService,
        )
    }
}
package be.mauricecantaert.mobileappdevandroid.data

import android.content.Context
import androidx.room.Room
import be.mauricecantaert.mobileappdevandroid.data.database.NewsDao
import be.mauricecantaert.mobileappdevandroid.data.database.NewsDatabase
import be.mauricecantaert.mobileappdevandroid.network.NewsApiService
import be.mauricecantaert.mobileappdevandroid.network.WebService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val newsRepository: NewsRepository
}

/**
 * Default implementation of [AppContainer] providing access to the necessary components for the application.
 *
 * @property context The application context used to initialize various components.
 */
class DefaultAppContainer(
    private val context: Context,
) : AppContainer {
    private val restApiBaseUrl = "https://api.spaceflightnewsapi.net/v4/"

    private val json = Json { ignoreUnknownKeys = true }

    private val logger =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val newsFetcherApi = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(restApiBaseUrl)
        .client(client)
        .build()

    private val htmlFetcherApi = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl("http://localhost/") // Retrofit requires a baseurl even though we'll pass the url along as a parameter
        .client(client)
        .build()

    private val newsApiRetrofitService: NewsApiService by lazy {
        newsFetcherApi.create(NewsApiService::class.java)
    }

    private val webRetrofitService: WebService by lazy {
        htmlFetcherApi.create(WebService::class.java)
    }

    private val database: NewsDatabase by lazy {
        Room.databaseBuilder(context, NewsDatabase::class.java, "news_database")
            .build()
    }

    private val newsDao: NewsDao by lazy {
        database.newsDao()
    }

    /**
     * Lazy-initialized [NewsRepository] instance combining APIs for remote and local data sources.
     * Provides the [NewsRepository] interface to manage news articles, both fetched online and saved offline.
     */
    override val newsRepository: NewsRepository by lazy {
        NewsApiRepository(
            newsApiService = newsApiRetrofitService,
            newsDao = newsDao,
            webService = webRetrofitService,
        )
    }
}

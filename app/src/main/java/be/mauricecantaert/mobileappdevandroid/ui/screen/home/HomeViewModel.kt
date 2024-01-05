package be.mauricecantaert.mobileappdevandroid.ui.screen.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.mauricecantaert.mobileappdevandroid.api.NewsApiApplication
import be.mauricecantaert.mobileappdevandroid.data.FetchOption
import be.mauricecantaert.mobileappdevandroid.data.NewsRepository
import be.mauricecantaert.mobileappdevandroid.model.ErrorType
import be.mauricecantaert.mobileappdevandroid.model.NewsArticleListState
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class HomeViewModel(
    private val newsApiRepository: NewsRepository,
) : ViewModel() {

    var apiState: NewsArticlesApiState by mutableStateOf(NewsArticlesApiState.Loading)

    private val _uiState = MutableStateFlow(NewsArticleListState())
    val uiState = _uiState.asStateFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApiApplication)
                val newsRepository =
                    application.container.newsRepository
                HomeViewModel(
                    newsApiRepository = newsRepository,
                )
            }
        }
    }

    private fun getOffset(fetchOption: FetchOption): Int {
        val url = when (fetchOption) {
            FetchOption.NEXT -> uiState.value.navigationDetails.next
            FetchOption.PREVIOUS -> uiState.value.navigationDetails.previous
            FetchOption.RESTART -> null
        }
            ?: return 0 // get which url to parse the offset from, or immediately return offset 0 if option is restart

        val urlParameters = url.split("?").getOrNull(1) // Extract query parameters part
        val offsetParameter = urlParameters
            ?.split("&") // get all parameters into a string list of parameters
            ?.map { it.split("=") } // split all parameters into string lists of parameter option & parameter value
            ?.find { it[0] == "offset" } // find the offset parameter, or null if it doesn't exist

        return offsetParameter?.getOrNull(1)?.toIntOrNull()
            ?: 0 // return the offset, or 0 if there is none
    }

    fun getNewsArticles(fetchOption: FetchOption, networkAvailable: Boolean) {
        apiState = NewsArticlesApiState.Loading
        viewModelScope.launch {
            apiState = try {
                if (!networkAvailable) {
                    NewsArticlesApiState.Error(ErrorType.DEVICE_OFFLINE)
                } else {
                    val offset = getOffset(fetchOption)
                    val response = newsApiRepository.getArticles(offset)
                    _uiState.update {
                        it.copy(
                            navigationDetails = response.first,
                            newsArticles = response.second,
                        )
                    }
                    NewsArticlesApiState.Success
                }
            } catch (e: HttpException) {
                Log.e("HomeViewModel Http Exception", e.message ?: e.toString())
                NewsArticlesApiState.Error(ErrorType.EXCEPTION)
            } catch (e: SocketTimeoutException) {
                Log.e("HomeViewModel Socket Exception", e.message ?: e.toString())
                NewsArticlesApiState.Error(ErrorType.DEVICE_OFFLINE)
            }
        }
    }

    fun setFavorited(id: Long, wantsAdded: Boolean) {
        viewModelScope.launch {
            if (wantsAdded) {
                newsApiRepository.favoriteArticle(uiState.value.newsArticles.find { it.id == id }!!)
            } else {
                newsApiRepository.unfavoriteArticle(id)
            }
            _uiState.update {
                it.copy(
                    newsArticles = it.newsArticles.map { newsArticle ->
                        if (newsArticle.id == id) {
                            newsArticle.copy(isFavorited = wantsAdded)
                        } else {
                            newsArticle
                        }
                    },
                )
            }
        }
    }
}

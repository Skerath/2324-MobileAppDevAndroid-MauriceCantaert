package be.mauricecantaert.mobileappdevandroid.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.mauricecantaert.mobileappdevandroid.api.NewsApiApplication
import be.mauricecantaert.mobileappdevandroid.data.NewsRepository
import be.mauricecantaert.mobileappdevandroid.model.NewsArticleListState
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(
    private val newsApiRepository: NewsRepository,
) : ViewModel() {

    var apiState: NewsArticlesApiState by mutableStateOf(NewsArticlesApiState.Loading)

    private val _uiState = MutableStateFlow(NewsArticleListState())
    val uiState = _uiState.asStateFlow()

    init {
        getNewsArticles(FetchOption.RESTART)
    }

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

    private fun getOffset(url: String?): Int {
        if (url == null) return 0
        val urlParameters = url.split("?").getOrNull(1) // Extract query parameters part
        val offsetParameter = urlParameters
            ?.split("&") // get all parameters into a string list of parameters
            ?.map { it.split("=") } // split all parameters into string lists of parameter option & parameter value
            ?.find { it[0] == "offset" } // find the offset parameter, or null if it doesn't exist

        return offsetParameter?.getOrNull(1)?.toIntOrNull()
            ?: 0 // return the offset, or 0 if there is none
    }

    fun getNewsArticles(option: FetchOption) {
        viewModelScope.launch {
            apiState = try {
                NewsArticlesApiState.Loading

                val offset = when (option) {
                    FetchOption.NEXT -> getOffset(uiState.value.navigationDetails.next)
                    FetchOption.PREVIOUS -> getOffset(uiState.value.navigationDetails.previous)
                    FetchOption.RESTART -> 0
                }

                val response = newsApiRepository.getArticles(offset)
                _uiState.update {
                    it.copy(
                        navigationDetails = response.first,
                        newsArticles = response.second,
                    )
                }

                NewsArticlesApiState.Success
            } catch (e: HttpException) {
                NewsArticlesApiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}
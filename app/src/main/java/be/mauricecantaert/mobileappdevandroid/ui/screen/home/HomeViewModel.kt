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
        getNewsArticles()
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

    private fun getNewsArticles() {
        viewModelScope.launch {
            apiState = try {
                NewsArticlesApiState.Loading
                val listResult = newsApiRepository.getArticles()
                _uiState.update {
                    it.copy(newsArticles = listResult)
                }
                NewsArticlesApiState.Success
            } catch (e: HttpException) {
                NewsArticlesApiState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    fun refresh() {
        getNewsArticles()
    }
}

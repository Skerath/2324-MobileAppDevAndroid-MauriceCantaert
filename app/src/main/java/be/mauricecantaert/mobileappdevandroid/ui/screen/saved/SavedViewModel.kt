package be.mauricecantaert.mobileappdevandroid.ui.screen.saved

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
import be.mauricecantaert.mobileappdevandroid.data.NewsRepository
import be.mauricecantaert.mobileappdevandroid.model.ErrorType
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.model.NewsArticleText
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel responsible for handling saved articles.
 *
 * @property newsApiRepository The repository providing access to news-related data and operations.
 */
class SavedViewModel(
    private val newsApiRepository: NewsRepository,
) : ViewModel() {

    var apiState: NewsArticlesApiState by mutableStateOf(NewsArticlesApiState.Loading)

    lateinit var favoritedArticles: StateFlow<List<Pair<NewsArticle, NewsArticleText>>>

    init {
        getNewsArticles()
    }

    /**
     * Companion object to provide a [ViewModelProvider.Factory] to create instances of [SavedViewModel].
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApiApplication)
                val newsRepository =
                    application.container.newsRepository
                SavedViewModel(
                    newsApiRepository = newsRepository,
                )
            }
        }
    }

    private fun getNewsArticles() {
        try {
            apiState = NewsArticlesApiState.Loading

            favoritedArticles = newsApiRepository.getFavoritedArticles()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = listOf(),
                )

            apiState = NewsArticlesApiState.Success
        } catch (e: IOException) {
            Log.e("SavedViewModel IO Exception", e.message ?: e.toString())
            apiState = NewsArticlesApiState.Error(ErrorType.EXCEPTION)
        }
    }

    fun removeFavorited(id: Long) {
        viewModelScope.launch {
            newsApiRepository.unfavoriteArticle(id)
        }
    }
}

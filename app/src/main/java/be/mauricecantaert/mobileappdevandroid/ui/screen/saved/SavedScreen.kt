package be.mauricecantaert.mobileappdevandroid.ui.screen.saved

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState
import be.mauricecantaert.mobileappdevandroid.model.getMessage
import be.mauricecantaert.mobileappdevandroid.ui.common.ErrorIndicator
import be.mauricecantaert.mobileappdevandroid.ui.common.LoadingIndicator
import be.mauricecantaert.mobileappdevandroid.ui.screen.saved.components.NoSavedArticlesAlert
import be.mauricecantaert.mobileappdevandroid.ui.screen.saved.components.OfflineNewsCard

@Composable
fun SavedScreen(
    savedViewModel: SavedViewModel,
    navigateHome: () -> Unit,
    context: Context = LocalContext.current,
) {
    val favoritedArticles by savedViewModel.favoritedArticles.collectAsState()
    when (val state = savedViewModel.apiState) {
        is NewsArticlesApiState.Error -> ErrorIndicator(text = state.errorType.getMessage(context))
        NewsArticlesApiState.Loading -> LoadingIndicator()
        NewsArticlesApiState.Success -> {
            if (favoritedArticles.isEmpty()) {
                NoSavedArticlesAlert(
                    onDismiss = { navigateHome() },
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(favoritedArticles) { article ->
                        OfflineNewsCard(
                            modifier = Modifier.padding(8.dp),
                            article = article.first,
                            removeFavorite = { savedViewModel.removeFavorited(it) },
                            articleText = article.second.articleText,
                        )
                    }
                }
            }
        }
    }
}

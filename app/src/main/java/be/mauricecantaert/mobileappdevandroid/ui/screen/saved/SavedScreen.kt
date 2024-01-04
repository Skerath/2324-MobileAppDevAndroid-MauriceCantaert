package be.mauricecantaert.mobileappdevandroid.ui.screen.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState
import be.mauricecantaert.mobileappdevandroid.ui.common.LoadingIndicator
import be.mauricecantaert.mobileappdevandroid.ui.common.newsCard.NewsCard

@Composable
fun SavedScreen(
    savedViewModel: SavedViewModel,
) {
    val favoritedArticles by savedViewModel.favoritedArticles.collectAsState()
    when (val state = savedViewModel.apiState) {
        is NewsArticlesApiState.Error -> Text(text = state.errorMessage)
        NewsArticlesApiState.Loading -> LoadingIndicator()
        NewsArticlesApiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(350.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(favoritedArticles) { article ->
                    NewsCard(
                        modifier = Modifier.padding(8.dp),
                        article = article.first,
                        isFavorite = true,
                        setFavorite = {},
                    )
                }
            }
        }
    }
}

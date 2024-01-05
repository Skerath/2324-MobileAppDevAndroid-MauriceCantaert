package be.mauricecantaert.mobileappdevandroid.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState
import be.mauricecantaert.mobileappdevandroid.ui.common.BottomNewsArticleNavigation
import be.mauricecantaert.mobileappdevandroid.ui.common.LoadingIndicator
import be.mauricecantaert.mobileappdevandroid.ui.screen.home.components.NewsCard

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
) {
    val uiState by homeViewModel.uiState.collectAsState()
    Scaffold(
        bottomBar = {
            BottomNewsArticleNavigation(
                navigate = { homeViewModel.getNewsArticles(it) },
                canNavigatePrevious = uiState.navigationDetails.previous != null,
                canNavigateNext = uiState.navigationDetails.next != null,
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        when (val state = homeViewModel.apiState) {
            is NewsArticlesApiState.Error -> Text(text = state.errorMessage)
            NewsArticlesApiState.Loading -> LoadingIndicator()
            NewsArticlesApiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(350.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(it),
                ) {
                    items(uiState.newsArticles) { article ->
                        NewsCard(
                            modifier = Modifier.padding(8.dp),
                            article = article,
                            isFavorite = article.isFavorited,
                            setFavorite = { isFavorited -> homeViewModel.setFavorited(article.id, isFavorited) },
                        )
                    }
                }
            }
        }
    }
}

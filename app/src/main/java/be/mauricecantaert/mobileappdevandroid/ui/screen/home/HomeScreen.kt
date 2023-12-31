package be.mauricecantaert.mobileappdevandroid.ui.screen.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.data.FetchOption
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState
import be.mauricecantaert.mobileappdevandroid.model.getMessage
import be.mauricecantaert.mobileappdevandroid.ui.common.BottomNewsArticleNavigation
import be.mauricecantaert.mobileappdevandroid.ui.common.ErrorIndicator
import be.mauricecantaert.mobileappdevandroid.ui.common.LoadingIndicator
import be.mauricecantaert.mobileappdevandroid.ui.screen.home.components.NewsCard

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    networkAvailable: Boolean,
    context: Context = LocalContext.current,
    pinnedBottomBar: Boolean,
) {
    val uiState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(networkAvailable) {
        if (networkAvailable) homeViewModel.getNewsArticles(FetchOption.RESTART, true)
    }

    Scaffold(
        bottomBar = {
            if (pinnedBottomBar) {
                BottomNewsArticleNavigation(
                    navigate = { homeViewModel.getNewsArticles(it, networkAvailable) },
                    canNavigatePrevious = uiState.navigationDetails.previous != null,
                    canNavigateNext = uiState.navigationDetails.next != null,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValue ->
        when (val state = homeViewModel.apiState) {
            is NewsArticlesApiState.Error -> ErrorIndicator(state.errorType.getMessage(context))
            NewsArticlesApiState.Loading -> LoadingIndicator()
            NewsArticlesApiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(paddingValue),
                ) {
                    items(uiState.newsArticles) { article ->
                        NewsCard(
                            modifier = Modifier.padding(8.dp),
                            article = article,
                            isFavorite = article.isFavorited,
                            hasInternetAccess = networkAvailable,
                            setFavorite = { isFavorited ->
                                homeViewModel.setFavorited(
                                    article.id,
                                    isFavorited,
                                )
                            },
                        )
                    }
                    item {
                        if (!pinnedBottomBar) {
                            BottomNewsArticleNavigation(
                                navigate = { homeViewModel.getNewsArticles(it, networkAvailable) },
                                canNavigatePrevious = uiState.navigationDetails.previous != null,
                                canNavigateNext = uiState.navigationDetails.next != null,
                            )
                        }
                    }
                }
            }
        }
    }
}

package be.mauricecantaert.mobileappdevandroid.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.R
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState
import be.mauricecantaert.mobileappdevandroid.ui.common.LoadingIndicator
import be.mauricecantaert.mobileappdevandroid.ui.common.NewsCard

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
) {
    val uiState by homeViewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = { homeViewModel.getNewsArticles(FetchOption.PREVIOUS) },
                        enabled = uiState.navigationDetails.previous != null,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_refresh),
                        )
                    }
                    IconButton(
                        onClick = { homeViewModel.getNewsArticles(FetchOption.NEXT) },
                        enabled = uiState.navigationDetails.next != null,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = stringResource(id = R.string.navigate_refresh),
                        )
                    }
                },
                floatingActionButton = {
                    IconButton(
                        onClick = { homeViewModel.getNewsArticles(FetchOption.RESTART) },
                        enabled = uiState.navigationDetails.next != null,
                    ) {
                        Icon(
                            Icons.Filled.Refresh,
                            contentDescription = stringResource(id = R.string.navigate_refresh),
                        )
                    }
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        when (val state = homeViewModel.apiState) {
            is NewsArticlesApiState.Error -> Text(text = state.errorMessage)
            NewsArticlesApiState.Loading -> LoadingIndicator()
            NewsArticlesApiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(250.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(it),
                ) {
                    items(uiState.newsArticles) { article ->
                        NewsCard(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(width = 240.dp, height = 200.dp),
                            article = article,
                        )
                    }
                }
            }
        }
    }
}

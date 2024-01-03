package be.mauricecantaert.mobileappdevandroid.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.R
import be.mauricecantaert.mobileappdevandroid.ui.common.NewsCard

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
) {
    val uiState by homeViewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO */ },
                content = {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = stringResource(id = R.string.navigate_refresh),
                    )
                },
                modifier = Modifier
                    .padding(all = 16.dp),
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(250.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(paddingValues),
        ) {
            items(uiState.newsArticles) { article ->
                NewsCard(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 240.dp, height = 140.dp),
                    article = article,
                )
            }
        }
    }
}

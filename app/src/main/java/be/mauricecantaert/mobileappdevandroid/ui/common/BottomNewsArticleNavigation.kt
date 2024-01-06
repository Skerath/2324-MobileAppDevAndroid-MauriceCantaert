package be.mauricecantaert.mobileappdevandroid.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import be.mauricecantaert.mobileappdevandroid.R
import be.mauricecantaert.mobileappdevandroid.data.FetchOption

@Composable
fun BottomNewsArticleNavigation(
    modifier: Modifier = Modifier,
    navigate: (FetchOption) -> Unit,
    canNavigatePrevious: Boolean,
    canNavigateNext: Boolean,
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(
                onClick = { navigate(FetchOption.PREVIOUS) },
                enabled = canNavigatePrevious,
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_previous),
                )
            }
            IconButton(
                onClick = { navigate(FetchOption.NEXT) },
                enabled = canNavigateNext,
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(id = R.string.navigate_refresh),
                )
            }
        },
        floatingActionButton = {
            IconButton(
                onClick = { navigate(FetchOption.RESTART) },
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = stringResource(id = R.string.navigate_next),
                )
            }
        },
    )
}

package be.mauricecantaert.mobileappdevandroid.ui.common.newsCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.R

@Composable
fun NewsCardButtons(
    isFavorite: Boolean,
    setFavorite: (Boolean) -> Unit,
    navigateArticle: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        IconButton(
            onClick = { setFavorite(!isFavorite) },
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Star else Icons.TwoTone.Star,
                contentDescription = stringResource(id = R.string.news_card_favorite),
                modifier = Modifier.size(24.dp),
            )
        }
        IconButton(
            onClick = { navigateArticle() },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.news_card_read),
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

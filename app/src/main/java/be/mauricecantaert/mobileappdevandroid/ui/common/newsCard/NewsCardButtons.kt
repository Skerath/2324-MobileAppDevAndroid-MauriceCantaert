package be.mauricecantaert.mobileappdevandroid.ui.common.newsCard

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.R

@Composable
fun NewsCardButtons(
    isFavorite: Boolean,
    hasNetworkAccess: Boolean,
    setFavorite: (Boolean) -> Unit,
    navigateArticle: () -> Unit,
    context: Context = LocalContext.current,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        IconButton(
            onClick = {
                if (!hasNetworkAccess && !isFavorite) { // allow unfavoriting even though there is no network connection
                    Toast.makeText(
                        context,
                        context.getString(R.string.errorToast_offline),
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    setFavorite(!isFavorite)
                }
            },
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Star else Icons.TwoTone.Star,
                contentDescription = stringResource(id = if (isFavorite) R.string.news_card_favorite else R.string.news_card_unfavorite),
                modifier = Modifier.size(24.dp),
            )
        }
        IconButton(
            onClick = {
                if (!hasNetworkAccess) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.errorToast_offline),
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    navigateArticle()
                }
            },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.news_card_read),
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

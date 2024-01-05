package be.mauricecantaert.mobileappdevandroid.ui.screen.saved.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.ui.common.newsCard.components.NewsCardButtons
import be.mauricecantaert.mobileappdevandroid.ui.common.newsCard.components.NewsCardHeader
import be.mauricecantaert.mobileappdevandroid.ui.common.newsCard.components.NewsCardImage

@Composable
fun OfflineNewsCard(
    modifier: Modifier = Modifier,
    article: NewsArticle,
    articleText: String,
    removeFavorite: (Long) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(bottom = 8.dp),
            ) {
                NewsCardImage(imageUrl = article.imageUrl)
                Spacer(modifier = Modifier.width(16.dp))
                NewsCardHeader(
                    modifier = Modifier.weight(1f),
                    title = article.title,
                    author = article.author,
                    publishDate = article.publishedAt,
                )
            }

            Text(
                text = if (isExpanded) articleText else article.summary,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
            )

            NewsCardButtons(
                isFavorite = true,
                setFavorite = { removeFavorite(article.id) },
                navigateArticle = { isExpanded = !isExpanded },
            )
        }
    }
}

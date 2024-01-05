package be.mauricecantaert.mobileappdevandroid.ui.screen.home.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.ui.common.newsCard.components.NewsCardButtons
import be.mauricecantaert.mobileappdevandroid.ui.common.newsCard.components.NewsCardHeader
import be.mauricecantaert.mobileappdevandroid.ui.common.newsCard.components.NewsCardImage

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    article: NewsArticle,
    isFavorite: Boolean,
    setFavorite: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val readArticleIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(article.articleUrl)) }

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
                text = article.summary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
            )

            NewsCardButtons(
                isFavorite = isFavorite,
                setFavorite = { setFavorite(it) },
                navigateArticle = { context.startActivity(readArticleIntent) },
            )
        }
    }
}

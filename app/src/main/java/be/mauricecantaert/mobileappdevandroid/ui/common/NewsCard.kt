package be.mauricecantaert.mobileappdevandroid.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    article: NewsArticle,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        modifier = modifier,
    ) {
        Text(
            text = article.title,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = article.summary,
            textAlign = TextAlign.Start,
        )
    }
}

package be.mauricecantaert.mobileappdevandroid.ui.common.newsCard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mauricecantaert.mobileappdevandroid.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NewsCardHeader(
    modifier: Modifier = Modifier,
    title: String,
    author: String,
    publishDate: String,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 4.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = stringResource(id = R.string.news_card_author, author),
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        Text(
            text = formatPublishDate(publishDate),
            color = Color.Gray,
        )
    }
}

private fun formatPublishDate(iso8601String: String): String = LocalDateTime
    .parse(iso8601String, DateTimeFormatter.ISO_DATE_TIME)
    .format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH))

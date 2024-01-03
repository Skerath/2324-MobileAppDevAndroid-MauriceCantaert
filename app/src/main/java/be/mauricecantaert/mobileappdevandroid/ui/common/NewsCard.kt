package be.mauricecantaert.mobileappdevandroid.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    title: String,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        modifier = modifier,
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

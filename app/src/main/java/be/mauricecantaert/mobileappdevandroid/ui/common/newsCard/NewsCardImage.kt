package be.mauricecantaert.mobileappdevandroid.ui.common.newsCard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.ui.common.ErrorIcon
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun NewsCardImage(
    imageUrl: String,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(bottom = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(RoundedCornerShape(10.dp)),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() },
                error = { ErrorIcon() },
            )
        }
    }
}

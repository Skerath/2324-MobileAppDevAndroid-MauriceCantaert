package be.mauricecantaert.mobileappdevandroid.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.R

@Composable
fun ErrorIcon() {
    Icon(
        imageVector = Icons.Filled.Warning,
        contentDescription = stringResource(id = R.string.error_indicator),
        modifier = Modifier.size(24.dp),
        tint = MaterialTheme.colorScheme.error,
    )
}

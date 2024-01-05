package be.mauricecantaert.mobileappdevandroid.ui.screen.saved.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.R
import be.mauricecantaert.mobileappdevandroid.ui.common.ErrorIcon

@Composable
fun NoSavedArticlesAlert(
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            ErrorIcon()
        },
        title = {
            Text(text = stringResource(R.string.error_no_saved_title))
        },
        text = {
            Text(text = stringResource(R.string.error_no_saved_body))
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.padding(8.dp),
            ) {
                Text(text = stringResource(R.string.error_no_saved_dismiss))
            }
        },
    )
}

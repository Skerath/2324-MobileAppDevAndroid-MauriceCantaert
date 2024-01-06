package be.mauricecantaert.mobileappdevandroid.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.R
import be.mauricecantaert.mobileappdevandroid.navigation.NavigationRoutes

@Composable
fun NavigationDrawer(
    context: Context,
    onClick: (String) -> Unit,
) {
    ModalDrawerSheet(
        modifier = Modifier.alpha(0.9f),
    ) {
        Spacer(
            modifier = Modifier.height(16.dp),
        )
        NavigationRoutes.entries.forEach { item ->
            NavigationItem(
                modifier = Modifier.testTag(
                    stringResource(
                        id = R.string.testTag_navigateHamburger_button,
                        item.name,
                    ),
                ),
                title = item.getString(context),
                onClick = { onClick(item.name) },
            )
        }
    }
}

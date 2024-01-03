package be.mauricecantaert.mobileappdevandroid.ui.navigation.drawer

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import be.mauricecantaert.mobileappdevandroid.ui.navigation.NavigationRoutes

@Composable
fun NavigationDrawer(
    context: Context,
    onClick: (String) -> Unit,
) {
    ModalDrawerSheet(
//        drawerContentColor = Color.White,
        modifier = Modifier
            .alpha(0.9f),
    ) {
        Spacer(
            modifier = Modifier.height(16.dp),
        )
        NavigationRoutes.entries.forEach { item ->
            NavigationItemComposable(
                title = item.getString(context),
                onClick = { onClick(item.name) },
            )
        }
    }
}

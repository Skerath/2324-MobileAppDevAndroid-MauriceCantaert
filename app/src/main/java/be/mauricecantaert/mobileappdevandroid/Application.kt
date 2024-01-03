package be.mauricecantaert.mobileappdevandroid

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Application(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
) {
    Text(
        text = "Test",
    )
}

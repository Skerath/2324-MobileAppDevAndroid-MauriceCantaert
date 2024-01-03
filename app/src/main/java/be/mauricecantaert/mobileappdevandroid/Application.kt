package be.mauricecantaert.mobileappdevandroid

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.mauricecantaert.mobileappdevandroid.ui.navigation.NavigationRoutes
import be.mauricecantaert.mobileappdevandroid.ui.navigation.drawer.AppBar
import be.mauricecantaert.mobileappdevandroid.ui.navigation.drawer.NavigationDrawer
import kotlinx.coroutines.launch

@Composable
fun Application(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        gesturesEnabled = true,
        drawerContent = {
            NavigationDrawer(
                context = context,
                onClick = {
                    if (it == NavigationRoutes.Home.name) navController.popBackStack()
                    navController.navigate(it)
                    scope.launch {
                        drawerState.close()
                    }
                },
            )
        },
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    titleResourceId = NavigationRoutes.valueOf(
                        backStackEntry?.destination?.route
                            ?: NavigationRoutes.Home.name,
                    ).title,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                )
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.Home.name,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(NavigationRoutes.Home.name) {
                    Text(text = "Homepage")
                }
                composable(NavigationRoutes.Saved.name) {
                    Text(text = "Saved items page")
                }
            }
        }
    }
}

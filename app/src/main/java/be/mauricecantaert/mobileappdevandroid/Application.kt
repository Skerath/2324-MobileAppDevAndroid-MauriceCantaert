package be.mauricecantaert.mobileappdevandroid

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.mauricecantaert.mobileappdevandroid.data.FetchOption
import be.mauricecantaert.mobileappdevandroid.navigation.NavigationRoutes
import be.mauricecantaert.mobileappdevandroid.ui.navigation.AppBar
import be.mauricecantaert.mobileappdevandroid.ui.navigation.NavigationDrawer
import be.mauricecantaert.mobileappdevandroid.ui.screen.home.HomeScreen
import be.mauricecantaert.mobileappdevandroid.ui.screen.home.HomeViewModel
import be.mauricecantaert.mobileappdevandroid.ui.screen.saved.SavedScreen
import be.mauricecantaert.mobileappdevandroid.ui.screen.saved.SavedViewModel
import kotlinx.coroutines.launch

@Composable
fun Application(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val savedViewModel: SavedViewModel = viewModel(factory = SavedViewModel.Factory)

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
                    LaunchedEffect(backStackEntry) {
                        // Refetch latest articles when navigating to homescreen.
                        // Happens on both first launch & when navigating to home after editing favorites
                        homeViewModel.getNewsArticles(FetchOption.RESTART)
                    }
                    HomeScreen(
                        homeViewModel = homeViewModel,
                    )
                }
                composable(NavigationRoutes.Saved.name) {
                    SavedScreen(
                        savedViewModel = savedViewModel,
                        navigateHome = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}

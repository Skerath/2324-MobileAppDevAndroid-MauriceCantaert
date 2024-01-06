package be.mauricecantaert.mobileappdevandroid

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import be.mauricecantaert.mobileappdevandroid.navigation.NavigationRoutes
import be.mauricecantaert.mobileappdevandroid.utility.assertCurrentRouteName
import be.mauricecantaert.mobileappdevandroid.utility.onNodeWithStringTestTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            Application(navController = navController)
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(NavigationRoutes.Home.name)
    }

    @Test
    fun navHost_homePage_hasHamburgerMenuAndNoBackwardsArrow() {
        composeTestRule.onNodeWithStringTestTag(R.string.testTag_navigateBack).assertDoesNotExist()
        composeTestRule.onNodeWithStringTestTag(R.string.testTag_navigateHamburger).assertExists()
    }

    @Test
    fun navHost_homePage_navigateToSaved() {
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateHamburger)
            .performClick()
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateHamburger_button, NavigationRoutes.Saved.name)
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.Saved.name)
    }

    @Test
    fun navHost_savedPage_navigateHomeUsingHamburgerMenu() {
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateHamburger)
            .performClick()
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateHamburger_button, NavigationRoutes.Home.name)
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.Home.name)
    }

    @Test
    fun navHost_savedPage_navigateHomeUsingBackArrow() {
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateHamburger)
            .performClick()
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateHamburger_button, NavigationRoutes.Saved.name)
            .performClick()
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateBack)
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.Home.name)
    }
}

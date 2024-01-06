package be.mauricecantaert.mobileappdevandroid

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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

/**
 * Test suite for navigation within the app.
 */
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    /**
     * Sets up the [TestNavHostController] for navigation testing.
     */
    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            Application(navController = navController, windowSize = WindowWidthSizeClass.Compact)
        }
    }

    /**
     * Verifies that the start destination is the Home screen.
     */
    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(NavigationRoutes.Home.name)
    }

    /**
     * Verifies that the Home page has a hamburger menu and no backwards navigation arrow.
     */
    @Test
    fun navHost_homePage_hasHamburgerMenuAndNoBackwardsArrow() {
        composeTestRule.onNodeWithStringTestTag(R.string.testTag_navigateBack).assertDoesNotExist()
        composeTestRule.onNodeWithStringTestTag(R.string.testTag_navigateHamburger).assertExists()
    }

    /**
     * Verifies navigation from the Home page to the Saved page.
     */
    @Test
    fun navHost_homePage_navigateToSaved() {
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateHamburger)
            .performClick()
        composeTestRule.onNodeWithStringTestTag(
            id = R.string.testTag_navigateHamburger_button,
            NavigationRoutes.Saved.name,
        )
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.Saved.name)
    }

    /**
     * Verifies navigation from the Saved page back to the Home page using the hamburger menu.
     */
    @Test
    fun navHost_savedPage_navigateHomeUsingHamburgerMenu() {
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateHamburger)
            .performClick()
        composeTestRule.onNodeWithStringTestTag(
            id = R.string.testTag_navigateHamburger_button,
            NavigationRoutes.Home.name,
        )
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.Home.name)
    }

    /**
     * Verifies navigation from the Saved page back to the Home page using the back arrow.
     */
    @Test
    fun navHost_savedPage_navigateHomeUsingBackArrow() {
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateHamburger)
            .performClick()
        composeTestRule.onNodeWithStringTestTag(
            id = R.string.testTag_navigateHamburger_button,
            NavigationRoutes.Saved.name,
        )
            .performClick()
        composeTestRule.onNodeWithStringTestTag(id = R.string.testTag_navigateBack)
            .performClick()
        navController.assertCurrentRouteName(NavigationRoutes.Home.name)
    }
}

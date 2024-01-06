package be.mauricecantaert.mobileappdevandroid.utility

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import junit.framework.TestCase.assertEquals

/**
 * Asserts that the current route within a NavController matches the expected route name.
 *
 * @param expectedRouteName The expected route name to compare with the current route in the NavController.
 */
fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}

/**
 * Returns a SemanticsNodeInteraction for a Compose node identified by its string test tag using a StringRes ID.
 *
 * @param id The StringRes ID representing the string resource used as a test tag to identify the Compose node.
 * @return A SemanticsNodeInteraction for the identified Compose node.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringTestTag(
    @StringRes id: Int,
): SemanticsNodeInteraction = onNodeWithTag(activity.getString(id))

/**
 * Returns a SemanticsNodeInteraction for a Compose node identified by its string test tag using a formatted string resource.
 *
 * @param id The StringRes ID representing the string resource used as a test tag to identify the Compose node.
 * @param formatArg A string argument to be used as argument to specify which specific item
 * @return A SemanticsNodeInteraction for the identified Compose node.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringTestTag(
    @StringRes id: Int,
    formatArg: String,
): SemanticsNodeInteraction = onNodeWithTag(activity.getString(id, formatArg))

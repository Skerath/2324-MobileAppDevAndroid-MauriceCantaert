package be.mauricecantaert.mobileappdevandroid.navigation

import android.content.Context
import androidx.annotation.StringRes
import be.mauricecantaert.mobileappdevandroid.R
import be.mauricecantaert.mobileappdevandroid.navigation.NavigationRoutes.Home
import be.mauricecantaert.mobileappdevandroid.navigation.NavigationRoutes.Saved

/**
 * Enum class representing different navigation routes with associated titles.
 * [Home]: The home page
 * [Saved]: The saved articles page where favorited articles can be read while offline
 * @property title The string resource ID for the title associated with the navigation route.
 */
enum class NavigationRoutes(@StringRes val title: Int) {
    Home(title = R.string.home_title),
    Saved(title = R.string.saved_title),
    ;

    /**
     * Retrieves the string representation of the title associated with the navigation route.
     * @param context The context used to retrieve the string resource.
     * @return The string value associated with the navigation route title.
     */
    fun getString(context: Context): String {
        return context.getString(title)
    }
}

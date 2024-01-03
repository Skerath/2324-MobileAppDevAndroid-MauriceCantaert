package be.mauricecantaert.mobileappdevandroid.ui.navigation

import android.content.Context
import androidx.annotation.StringRes
import be.mauricecantaert.mobileappdevandroid.R

enum class NavigationRoutes(@StringRes val title: Int) {
    Home(title = R.string.home_title),
    Saved(title = R.string.saved_title),
    ;

    fun getString(context: Context): String {
        return context.getString(title)
    }
}

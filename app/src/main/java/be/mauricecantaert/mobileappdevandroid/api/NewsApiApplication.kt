package be.mauricecantaert.mobileappdevandroid.api

import android.app.Application
import be.mauricecantaert.mobileappdevandroid.data.AppContainer
import be.mauricecantaert.mobileappdevandroid.data.DefaultAppContainer

/**
 * Custom Application class responsible for initializing the application-wide components and dependencies.
 */
class NewsApiApplication : Application() {
    lateinit var container: AppContainer

    /**
     * Called when the application is starting. Initializes the application-wide components.
     */
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}

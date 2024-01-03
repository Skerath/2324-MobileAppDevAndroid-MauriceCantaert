package be.mauricecantaert.mobileappdevandroid.api

import android.app.Application
import be.mauricecantaert.mobileappdevandroid.data.AppContainer
import be.mauricecantaert.mobileappdevandroid.data.DefaultAppContainer

class NewsApiApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}

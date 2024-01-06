package be.mauricecantaert.mobileappdevandroid.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room Database class representing the local database for managing favorited news articles.
 */
@Database(
    entities = [DbNewsArticle::class],
    version = 1,
    exportSchema = false,
)
abstract class NewsDatabase : RoomDatabase() {
    /**
     * Provides access to the Data Access Object (DAO) for saved news articles.
     *
     * @return A [NewsDao] object used to perform database operations related to the user's favorite news articles for offline access.
     */
    abstract fun newsDao(): NewsDao
}

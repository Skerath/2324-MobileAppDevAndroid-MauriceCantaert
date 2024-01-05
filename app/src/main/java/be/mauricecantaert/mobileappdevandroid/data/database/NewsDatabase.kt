package be.mauricecantaert.mobileappdevandroid.data.database // ktlint-disable filename

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DbNewsArticle::class],
    version = 1,
    exportSchema = false,
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

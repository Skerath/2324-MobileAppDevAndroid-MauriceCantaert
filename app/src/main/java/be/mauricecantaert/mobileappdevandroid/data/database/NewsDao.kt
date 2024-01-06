package be.mauricecantaert.mobileappdevandroid.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for managing news articles in the local database.
 */
@Dao
interface NewsDao {
    /**
     * Inserts a news article into the database.
     *
     * @param item The [DbNewsArticle] object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticle(item: DbNewsArticle)

    /**
     * Retrieves all saved news articles from the database as a Flow.
     *
     * @return A [Flow] emitting a list of [DbNewsArticle] objects representing news articles.
     */
    @Query("SELECT * FROM article")
    fun getNewsArticles(): Flow<List<DbNewsArticle>>

    /**
     * Removes a news article from the database based on its ID.
     *
     * @param id The ID of the news article to be removed.
     */
    @Query("DELETE FROM article WHERE id = :id")
    suspend fun removeNewsArticle(id: Long)

    /**
     * Checks whether a news article is favorited based on its ID.
     *
     * @param id The ID of the news article to check.
     * @return `true` if the article is saved in the database and thus favorited, `true` otherwise
     */
    @Query("SELECT isFavorited FROM article WHERE ID = :id")
    suspend fun isFavorited(id: Long): Boolean
}

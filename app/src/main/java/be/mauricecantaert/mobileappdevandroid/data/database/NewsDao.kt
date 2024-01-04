package be.mauricecantaert.mobileappdevandroid.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticle(item: DbNewsArticle)

    @Query("SELECT * FROM article")
    fun getNewsArticles(): Flow<List<DbNewsArticle>>

    @Query("DELETE FROM article WHERE id = :id")
    suspend fun removeNewsArticle(id: Long)

    @Query("SELECT isFavorited FROM article WHERE ID = :id")
    suspend fun isFavorited(id: Long): Boolean
}

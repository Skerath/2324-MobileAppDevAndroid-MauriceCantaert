package be.mauricecantaert.mobileappdevandroid

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.mauricecantaert.mobileappdevandroid.data.database.NewsDao
import be.mauricecantaert.mobileappdevandroid.data.database.NewsDatabase
import be.mauricecantaert.mobileappdevandroid.data.database.asDomainObject
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.model.asDbArticle
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Test suite for DAO operations in [NewsDao].
 *
 * @RunWith(AndroidJUnit4::class)
 */
@RunWith(AndroidJUnit4::class)
class DaoTest {

    private lateinit var dao: NewsDao
    private lateinit var database: NewsDatabase

    private val offlineArticles = listOf(
        NewsArticle(
            id = 2000,
            title = "NASA instruments set to fly on Peregrine commercial lunar lander",
            articleUrl = "https://spacenews.com/nasa-instruments-set-to-fly-on-peregrine-commercial-lunar-lander/",
            imageUrl = "https://i0.wp.com/spacenews.com/wp-content/uploads/2024/01/53410071751_6bc6df93bb_k.jpg",
            author = "SpaceNews",
            summary = "As NASA prepares for the first launch of a commercial lunar lander carrying agency-sponsored payloads, it is trying to balance the science it can achieve with the challenges of landing on the moon and other emerging concerns.",
            publishedAt = "2024-01-05T03:42:26Z",
            isFavorited = true,
        ),
        NewsArticle(
            id = 22007,
            title = "GAO denies L3Harris protest over Ball Aerospace weather satellite instrument contract",
            articleUrl = "https://spacenews.com/gao-denies-l3harris-protest-over-ball-aerospace-weather-satellite-instrument-contract/",
            imageUrl = "https://i0.wp.com/spacenews.com/wp-content/uploads/2023/03/rsz_2022-09_geoxo_constellation-9-16-22-v2.png",
            author = "SpaceNews",
            summary = "The Government Accountability Office has rejected a protest filed by L3Harris over NASAâ€™s award of a contract to Ball Aerospace for a next-generation weather satellite instrument",
            publishedAt = "2024-01-04T23:12:25Z",
            isFavorited = true,
        ),
        NewsArticle(
            id = 22006,
            title = "Biden Administration to Consult with Navajo About Human Remains on the Moon",
            articleUrl = "https://spacepolicyonline.com/news/biden-administration-to-consult-with-navajo-about-human-remains-on-the-moon/",
            imageUrl = "https://spacepolicyonline.com/wp-content/uploads/2024/01/Buu-Nygren-Navajo-300x265.png",
            author = "SpacePolicyOnline.com",
            summary = "NASA said today the Biden Administration will consult with the Navajo Nation about its concerns that human remains are being placed on the Moon on landers developed through the agencyâ€™s [â€¦]",
            publishedAt = "2024-01-04T23:02:01Z",
            isFavorited = true,
        ),
    )

    /**
     * Adds a specified number of offline articles to the database.
     *
     * @param amount The number of articles to add to the database.
     */
    private suspend fun addOfflineArticles(amount: Int) {
        for (i in 0..<amount) dao.insertNewsArticle(offlineArticles[i].asDbArticle("Sample article text belonging to article ${offlineArticles[i].id}"))
    }

    /**
     * Initializes the in-memory database and retrieves the DAO before each test.
     */
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database =
            Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).allowMainThreadQueries()
                .build()
        dao = database.newsDao()
    }

    /**
     * Closes the database after each test.
     *
     * @throws IOException If an I/O error occurs while closing the database.
     */
    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    /**
     * Test case for inserting articles into the database, checking if it gets added and the offline articleText gets set correctly
     */
    @Test
    fun dao_insertArticlesInDb() = runBlocking {
        val amountToAdd = 3
        addOfflineArticles(amountToAdd)
        val retrievedDbArticles = dao.getNewsArticles().first()

        for (i in 0..<amountToAdd) {
            assert(offlineArticles.contains(retrievedDbArticles[i].asDomainObject().first)) // test if retrieved article exists in original list
            assert(retrievedDbArticles.find { it.articleText == "Sample article text belonging to article ${offlineArticles[i].id}" } != null) // test if article texts are being added correctly
        }
        assert(retrievedDbArticles.size == amountToAdd)
    }

    /**
     * Test case for removing an article from the database.
     */
    @Test
    fun dao_removeArticleFromDb() = runBlocking {
        val amountToAdd = 2
        addOfflineArticles(amountToAdd)
        var retrievedDbArticles = dao.getNewsArticles().first()
        assertEquals(retrievedDbArticles.size, 2)

        dao.removeNewsArticle(offlineArticles[0].id)
        retrievedDbArticles = dao.getNewsArticles().first()

        assertEquals(retrievedDbArticles.size, amountToAdd - 1)
    }
}

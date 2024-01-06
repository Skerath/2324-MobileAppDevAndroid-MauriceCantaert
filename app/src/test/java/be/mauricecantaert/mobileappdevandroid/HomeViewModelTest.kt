package be.mauricecantaert.mobileappdevandroid

import be.mauricecantaert.mobileappdevandroid.data.FetchOption
import be.mauricecantaert.mobileappdevandroid.data.NewsRepository
import be.mauricecantaert.mobileappdevandroid.model.NavigationDetails
import be.mauricecantaert.mobileappdevandroid.model.NewsArticle
import be.mauricecantaert.mobileappdevandroid.model.NewsArticlesApiState
import be.mauricecantaert.mobileappdevandroid.ui.screen.home.HomeViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var newsRepositoryMock: NewsRepository

    private lateinit var homeViewModel: HomeViewModel

    private val standardTestDispatcher = StandardTestDispatcher()

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

    private val nextOffsetAfterStart = "https://next.url/api/?offset=10"
    private val previousOffsetAfterStart = null

    private val nextOffsetAfterNext = "https://next.url/api/?offset=20"
    private val previousOffsetAfterNext = "https://previous.url/api/?offset=0"

    /**
     * Setup method executed before each test case.
     * Initializes the mocked news repository and sets up the [HomeViewModel].
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(standardTestDispatcher)
        runTest {
            Mockito.`when`(newsRepositoryMock.getArticles(0)).thenReturn(
                NavigationDetails(
                    next = nextOffsetAfterStart,
                    previous = previousOffsetAfterStart,
                )
                    to
                    offlineArticles,
            )
            Mockito.`when`(newsRepositoryMock.getArticles(10)).thenReturn(
                NavigationDetails(
                    next = nextOffsetAfterNext,
                    previous = previousOffsetAfterNext,
                )
                    to
                    offlineArticles,
            )
            homeViewModel = HomeViewModel(newsRepositoryMock)
            advanceUntilIdle()
        }
    }

    /**
     * Teardown method executed after each test case.
     * Resets main dispatcher after testing completion.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Test to fetch articles with network available and fetch option as [FetchOption.RESTART].
     * Verifies if the expected articles, navigation details, and API state are obtained.
     */
    @Test
    fun fetchArticles_withNetwork_fetchOptionRestart() = runTest {
        homeViewModel.getNewsArticles(fetchOption = FetchOption.RESTART, true)
        assert(homeViewModel.apiState is NewsArticlesApiState.Loading)
        advanceUntilIdle()

        val viewModelUiState = homeViewModel.uiState.first()
        assertEquals(offlineArticles, viewModelUiState.newsArticles)
        assertEquals(nextOffsetAfterStart, viewModelUiState.navigationDetails.next)
        assertEquals(previousOffsetAfterStart, viewModelUiState.navigationDetails.previous)
        assert(homeViewModel.apiState is NewsArticlesApiState.Success)
    }

    /**
     * Test to fetch articles with network available and fetch option as [FetchOption.NEXT].
     * Verifies if the expected navigation details and API state are obtained after fetching next articles.
     */
    @Test
    fun fetchArticles_withNetwork_fetchOptionNext() = runTest {
        homeViewModel.getNewsArticles(fetchOption = FetchOption.RESTART, true)
        advanceUntilIdle()
        homeViewModel.getNewsArticles(fetchOption = FetchOption.NEXT, true)
        advanceUntilIdle()

        val viewModelUiState = homeViewModel.uiState.first()
        assertEquals(nextOffsetAfterNext, viewModelUiState.navigationDetails.next)
        assertEquals(previousOffsetAfterNext, viewModelUiState.navigationDetails.previous)
        assert(homeViewModel.apiState is NewsArticlesApiState.Success)
    }

    /**
     * Test to fetch articles with network available and fetch option as [FetchOption.PREVIOUS].
     * Verifies if the expected navigation details and API state are obtained after fetching previous articles.
     */
    @Test
    fun fetchArticles_withNetwork_fetchOptionPrevious() = runTest {
        homeViewModel.getNewsArticles(fetchOption = FetchOption.RESTART, true)
        advanceUntilIdle()
        homeViewModel.getNewsArticles(fetchOption = FetchOption.PREVIOUS, true)
        advanceUntilIdle()

        val viewModelUiState = homeViewModel.uiState.first()
        assertEquals(nextOffsetAfterStart, viewModelUiState.navigationDetails.next)
        assertEquals(previousOffsetAfterStart, viewModelUiState.navigationDetails.previous)
        assert(homeViewModel.apiState is NewsArticlesApiState.Success)
    }

    /**
     * Test to fetch articles without network available.
     * Verifies if the empty articles list and error state are obtained when network is unavailable.
     */
    @Test
    fun fetchArticles_withoutNetwork() = runTest {
        homeViewModel.getNewsArticles(fetchOption = FetchOption.RESTART, false)
        assert(homeViewModel.apiState is NewsArticlesApiState.Loading)
        advanceUntilIdle()

        val viewModelUiState = homeViewModel.uiState.first()
        assertEquals(listOf<NewsArticle>(), viewModelUiState.newsArticles)
        assert(homeViewModel.apiState is NewsArticlesApiState.Error)
    }

    /**
     * Test to favorite an article with network available.
     * Verifies if an article is successfully favorited when network is available.
     */
    @Test
    fun favoriteArticle_withNetwork_happyFlow() = runTest {
        homeViewModel.getNewsArticles(fetchOption = FetchOption.RESTART, true)
        advanceUntilIdle()

        homeViewModel.setFavorited(offlineArticles[0].id, true)
        val viewModelUiState = homeViewModel.uiState.first()

        assert(viewModelUiState.newsArticles.find { it.id == offlineArticles[0].id }!!.isFavorited)
    }

    /**
     * Test to favorite an article.
     * Verifies if an article is successfully favorited when network is available.
     */
    @Test
    fun favoriteArticle_happyFlow() = runTest {
        homeViewModel.getNewsArticles(fetchOption = FetchOption.RESTART, true)
        advanceUntilIdle()

        homeViewModel.setFavorited(offlineArticles[0].id, true)
        val viewModelUiState = homeViewModel.uiState.first()

        assert(viewModelUiState.newsArticles.find { it.id == offlineArticles[0].id }!!.isFavorited)
    }

    /**
     * Test to unfavorite an article.
     * Verifies if an article is successfully unfavorited.
     */
    @Test
    fun unfavoriteArticle_happyFlow() = runTest {
        homeViewModel.getNewsArticles(fetchOption = FetchOption.RESTART, true)
        advanceUntilIdle()

        homeViewModel.setFavorited(offlineArticles[0].id, true)
        homeViewModel.setFavorited(offlineArticles[0].id, false)
        advanceUntilIdle()

        val viewModelUiState = homeViewModel.uiState.first()
        assertFalse(viewModelUiState.newsArticles.find { it.id == offlineArticles[0].id }!!.isFavorited)
    }
}

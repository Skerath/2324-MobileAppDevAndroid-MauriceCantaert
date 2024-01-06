package be.mauricecantaert.mobileappdevandroid.data

/**
 * Enum class representing different options for fetching data in a paginated manner.
 * [NEXT]: Fetches the next set of data.
 * [PREVIOUS]: Fetches the previous set of data.
 * [RESTART]: Restarts the fetching of data from the beginning.
 */
enum class FetchOption {
    NEXT,
    PREVIOUS,
    RESTART,
}

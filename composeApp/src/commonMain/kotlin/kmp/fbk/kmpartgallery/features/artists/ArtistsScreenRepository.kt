package kmp.fbk.kmpartgallery.features.artists

import kmp.fbk.kmpartgallery.local_storage.database.dao.ArtPieceDao

class ArtistsScreenRepository(
    private val artPieceDao: ArtPieceDao,
) {
    /**
     * Retrieves all artists from the database and groups them by their first letter.
     *
     * @return A map where keys are the first letters of artist names (as Strings) and values are
     * lists of artist names starting with that letter.
     */
    suspend fun getArtistsAndTheFirstLetterHeader(): Map<String, List<String>> {
        val artists = artPieceDao.getAllArtists()
        val artistsAndHeaders = mutableMapOf<String, List<String>>()

        artists.forEach { artist ->
            val firstLetter = artist.first().toString()
            if (artistsAndHeaders.containsKey(firstLetter)) {
                artistsAndHeaders[firstLetter] = artistsAndHeaders[firstLetter]!! + artist
            } else {
                artistsAndHeaders[firstLetter] = listOf(artist)
            }
        }
        return artistsAndHeaders
    }
}
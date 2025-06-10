package kmp.fbk.kmpartgallery.features.artists

import kmp.fbk.kmpartgallery.local_storage.database.dao.ArtPieceDao

class ArtistsScreenRepository(
    private val artPieceDao: ArtPieceDao,
) {
    suspend fun getAllArtists(): List<String> {
        return artPieceDao.getAllArtists()
    }
}
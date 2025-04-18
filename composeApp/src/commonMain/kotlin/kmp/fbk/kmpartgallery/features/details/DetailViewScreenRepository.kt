package kmp.fbk.kmpartgallery.features.details

import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.local_storage.database.dao.ArtPieceDao
import kmp.fbk.kmpartgallery.local_storage.database.mappers.toArtPiece
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DetailViewScreenRepository(
    private val artPieceDao: ArtPieceDao
) {
    suspend fun getArtPieceById(artPieceLocalId: Long): ArtPiece? {
        return artPieceDao.getArtPieceByIdFlow(artPieceLocalId).first()?.toArtPiece()
    }

    fun getArtPieceByIdFlow(artPieceLocalId: Long): Flow<ArtPiece?> {
        return artPieceDao.getArtPieceByIdFlow(artPieceLocalId).map {
            it?.toArtPiece()
        }
    }

}
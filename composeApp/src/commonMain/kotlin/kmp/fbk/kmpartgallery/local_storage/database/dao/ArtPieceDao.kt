package kmp.fbk.kmpartgallery.local_storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kmp.fbk.kmpartgallery.local_storage.database.entities.ArtPieceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtPieceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artPiece: ArtPieceEntity)

    @Query("SELECT * FROM ${ArtPieceEntity.TABLE_NAME}")
    suspend fun getAllArtPieces(): List<ArtPieceEntity>

    @Query("SELECT ${ArtPieceEntity.Companion.Column.OBJECT_ID} FROM ${ArtPieceEntity.TABLE_NAME}")
    suspend fun getAllArtPieceObjectIds(): List<Int?>

    @Query("SELECT * FROM ${ArtPieceEntity.TABLE_NAME}")
    fun getAllArtPiecesFlow(): Flow<List<ArtPieceEntity>>

}
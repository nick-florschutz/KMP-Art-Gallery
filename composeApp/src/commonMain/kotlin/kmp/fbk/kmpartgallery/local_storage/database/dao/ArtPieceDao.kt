package kmp.fbk.kmpartgallery.local_storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kmp.fbk.kmpartgallery.features.listscreen.ArtPieceLocalIdAndImage
import kmp.fbk.kmpartgallery.local_storage.database.entities.ArtPieceEntity
import kmp.fbk.kmpartgallery.local_storage.database.entities.IEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtPieceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artPiece: ArtPieceEntity)

    @Query("SELECT * FROM ${ArtPieceEntity.TABLE_NAME}")
    suspend fun getAllArtPieces(): List<ArtPieceEntity>

    @Query("""
        SELECT * FROM ${ArtPieceEntity.TABLE_NAME} WHERE ${IEntity.Companion.Column.LOCAL_ID} = :artPieceId
    """)
    fun getArtPieceByIdFlow(artPieceId: Long): Flow<ArtPieceEntity?>

    @Query("SELECT ${ArtPieceEntity.Companion.Column.OBJECT_ID} FROM ${ArtPieceEntity.TABLE_NAME}")
    suspend fun getAllArtPieceObjectIds(): List<Int?>

    @Query("SELECT * FROM ${ArtPieceEntity.TABLE_NAME}")
    fun getAllArtPiecesFlow(): Flow<List<ArtPieceEntity>>

    @Query("""
        SELECT ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} FROM ${ArtPieceEntity.TABLE_NAME}
         WHERE ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} IS NOT NULL AND 
         ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} != "" AND
         ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} != '' ORDER BY RANDOM() LIMIT 5
    """)
    suspend fun getFiveArtPiecePrimaryImages(): List<String>

    @Query("""
        SELECT ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} FROM ${ArtPieceEntity.TABLE_NAME}
         WHERE ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} IS NOT NULL AND 
         ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} != "" AND
         ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} != '' ORDER BY RANDOM() LIMIT 5
    """)
    fun getFiveArtPiecePrimaryImagesFlow(): Flow<List<String>>

    @Query("""
        SELECT * FROM ${ArtPieceEntity.TABLE_NAME}
         WHERE ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} IS NOT NULL AND 
         ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} != "" AND
         ${ArtPieceEntity.Companion.Column.PRIMARY_IMAGE} != '' ORDER BY RANDOM() LIMIT 5
    """)
    fun getFiveArtPiecesFlow(): Flow<List<ArtPieceEntity>>

}
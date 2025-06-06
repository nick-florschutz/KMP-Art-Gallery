package kmp.fbk.kmpartgallery.local_storage.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kmp.fbk.kmpartgallery.local_storage.database.dao.ArtPieceDao
import kmp.fbk.kmpartgallery.local_storage.database.dao.DepartmentDao
import kmp.fbk.kmpartgallery.local_storage.database.entities.ArtPieceEntity
import kmp.fbk.kmpartgallery.local_storage.database.entities.DepartmentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [DepartmentEntity::class, ArtPieceEntity::class],
    version = 1,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class ArtGalleryDatabase: RoomDatabase() {

    abstract fun departmentDao(): DepartmentDao

    abstract fun artPieceDao(): ArtPieceDao

}

fun <DatabaseType: RoomDatabase> getRoomDatabase(
    builder: RoomDatabase.Builder<DatabaseType>
): DatabaseType {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ArtGalleryDatabase> {
    override fun initialize(): ArtGalleryDatabase
}
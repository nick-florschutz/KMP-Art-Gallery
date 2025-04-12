package kmp.fbk.kmpartgallery.local_storage

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kmp.fbk.kmpartgallery.local_storage.dao.DepartmentDao
import kmp.fbk.kmpartgallery.local_storage.entities.DepartmentEntity
import kmp.fbk.kmpartgallery.local_storage.entities.DetailedMeasurementsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [DepartmentEntity::class],
    version = 1,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class ArtGalleryDatabase: RoomDatabase() {

    abstract fun departmentDao(): DepartmentDao

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
package kmp.fbk.kmpartgallery.local_storage

import androidx.room.Room
import androidx.room.RoomDatabase
import kmp.fbk.kmpartgallery.local_storage.database.ArtGalleryDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

fun artGalleryDatabaseBuilder(): RoomDatabase.Builder<ArtGalleryDatabase> {
    val dbFilePath = "${fileDirectory()}/art_gallery.db"
    return Room.databaseBuilder<ArtGalleryDatabase>(
        name = dbFilePath,
    )
}


@OptIn(ExperimentalForeignApi::class)
private fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path!!
}
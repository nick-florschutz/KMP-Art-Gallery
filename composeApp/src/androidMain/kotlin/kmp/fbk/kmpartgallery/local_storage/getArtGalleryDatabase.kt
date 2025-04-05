package kmp.fbk.kmpartgallery.local_storage

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun artGalleryDatabaseBuilder(ctx: Context): RoomDatabase.Builder<ArtGalleryDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("art_gallery.db")
    return Room.databaseBuilder<ArtGalleryDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
package kmp.fbk.kmpartgallery.di

import android.content.Context
import kmp.fbk.kmpartgallery.local_storage.artGalleryDatabaseBuilder
import kmp.fbk.kmpartgallery.local_storage.database.getRoomDatabase
import org.koin.dsl.module

fun getRoomDatabaseModule(context: Context) = module {
    single { getRoomDatabase(artGalleryDatabaseBuilder(context)) }
}

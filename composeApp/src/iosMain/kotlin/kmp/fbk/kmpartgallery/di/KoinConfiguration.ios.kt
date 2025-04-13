package kmp.fbk.kmpartgallery.di

import kmp.fbk.kmpartgallery.local_storage.artGalleryDatabaseBuilder
import kmp.fbk.kmpartgallery.local_storage.database.getRoomDatabase
import org.koin.dsl.module

val getRoomDatabaseModule = module {
    single { getRoomDatabase(artGalleryDatabaseBuilder()) }
}

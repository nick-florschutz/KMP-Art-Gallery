package kmp.fbk.kmpartgallery.di

import kmp.fbk.kmpartgallery.local_storage.artGalleryDatabaseBuilder
import kmp.fbk.kmpartgallery.local_storage.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

val getRoomDatabaseModule = module {
    single { getRoomDatabase(artGalleryDatabaseBuilder()) }
}

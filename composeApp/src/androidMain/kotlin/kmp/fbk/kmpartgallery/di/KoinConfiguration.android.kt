package kmp.fbk.kmpartgallery.di

import android.content.Context
import kmp.fbk.kmpartgallery.local_storage.artGalleryDatabaseBuilder
import kmp.fbk.kmpartgallery.local_storage.getRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

fun getRoomDatabaseModule(context: Context) = module {
    single { getRoomDatabase(artGalleryDatabaseBuilder(context)) }
}

//actual val platformModules: List<Module>
//    get() = listOf(
//        getRoomDatabaseModule,
//    )
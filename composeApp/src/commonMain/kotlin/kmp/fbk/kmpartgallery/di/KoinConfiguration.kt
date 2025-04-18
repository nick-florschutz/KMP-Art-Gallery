package kmp.fbk.kmpartgallery.di

import kmp.fbk.kmpartgallery.AppInitializer
import kmp.fbk.kmpartgallery.features.details.DetailViewScreenRepository
import kmp.fbk.kmpartgallery.features.listscreen.ListScreenRepository
import kmp.fbk.kmpartgallery.local_storage.database.ArtGalleryDatabase
import kmp.fbk.kmpartgallery.local_storage.database.dao.ArtPieceDao
import kmp.fbk.kmpartgallery.local_storage.database.dao.DepartmentDao
import kmp.fbk.kmpartgallery.local_storage.preferences.DataStoreRepository
import kmp.fbk.kmpartgallery.local_storage.preferences.createDataStore
import kmp.fbk.kmpartgallery.networking.download.ArtPieceDownloadMachine
import kmp.fbk.kmpartgallery.networking.download.DepartmentsDownloadMachine
import org.koin.core.module.Module
import org.koin.dsl.module

val daoModule = module {
    single<DepartmentDao> { get<ArtGalleryDatabase>().departmentDao() }
    single<ArtPieceDao> { get<ArtGalleryDatabase>().artPieceDao() }
}

val appInitializerModule = module {
    single { DataStoreRepository(dataStore = get()) }
    single { DepartmentsDownloadMachine(departmentDao = get(), dataStoreRepository = get()) }
    single { ArtPieceDownloadMachine(artPieceDao = get(), dataStoreRepository = get()) }
    single { AppInitializer(get(), get()) }
}

val listScreenModule = module {
    factory { ListScreenRepository(departmentDao = get(), artPieceDao = get()) }
}

val detailViewScreenModule = module {
    factory { DetailViewScreenRepository(artPieceDao = get()) }
}

fun appModules(): List<Module> = listOf(
    daoModule,
    appInitializerModule,
    listScreenModule,
    detailViewScreenModule,
)
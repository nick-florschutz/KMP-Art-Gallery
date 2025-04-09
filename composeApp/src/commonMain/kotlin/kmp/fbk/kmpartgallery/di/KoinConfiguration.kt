package kmp.fbk.kmpartgallery.di

import kmp.fbk.kmpartgallery.features.listscreen.ListScreenRepository
import kmp.fbk.kmpartgallery.features.listscreen.ListScreenViewModel
import kmp.fbk.kmpartgallery.local_storage.ArtGalleryDatabase
import kmp.fbk.kmpartgallery.local_storage.dao.DepartmentDao
import org.koin.core.module.Module
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module

val daoModule = module {
    single<DepartmentDao> { get<ArtGalleryDatabase>().departmentDao() }
}

fun appModules(): List<Module> = listOf(
    daoModule,
    module {
        factory { ListScreenRepository(departmentDao = get()) }
    }
)
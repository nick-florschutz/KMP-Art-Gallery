package kmp.fbk.kmpartgallery.di

import kmp.fbk.kmpartgallery.MetArtApi
import org.koin.core.module.Module
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module

//expect val platformContextModule: Module

fun createKoinConfiguration(): KoinConfiguration {
    return KoinConfiguration {
//        modules(platformContextModule)
        modules()
    }
}
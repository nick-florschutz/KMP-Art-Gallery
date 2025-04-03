package kmp.fbk.kmpartgallery.di

import org.koin.dsl.KoinConfiguration

//expect val platformContextModule: Module

fun createKoinConfiguration(): KoinConfiguration {
    return KoinConfiguration {
//        modules(platformContextModule)
        modules()
    }
}
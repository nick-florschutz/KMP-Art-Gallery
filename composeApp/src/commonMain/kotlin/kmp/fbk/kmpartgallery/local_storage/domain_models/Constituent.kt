package kmp.fbk.kmpartgallery.local_storage.domain_models

import kotlinx.serialization.Serializable

@Serializable
data class Constituent(
    val constituentID: Int,
    val role: String,
    val name: String,
    val constituentULANURL: String,
    val constituentWikidataURL: String,
)

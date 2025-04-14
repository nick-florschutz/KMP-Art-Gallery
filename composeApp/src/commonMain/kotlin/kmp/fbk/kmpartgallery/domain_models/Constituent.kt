package kmp.fbk.kmpartgallery.domain_models

import kotlinx.serialization.Serializable

@Serializable
data class Constituent(
    val constituentID: Int,
    val role: String,
    val name: String,
    val constituentULANURL: String,
    val constituentWikidataURL: String,
)

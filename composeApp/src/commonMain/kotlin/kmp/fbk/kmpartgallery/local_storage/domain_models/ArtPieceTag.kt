package kmp.fbk.kmpartgallery.local_storage.domain_models

import kotlinx.serialization.Serializable

@Serializable
data class ArtPieceTag(
    val term: String,
    val aATURL: String,
    val wikidataURL: String,
)

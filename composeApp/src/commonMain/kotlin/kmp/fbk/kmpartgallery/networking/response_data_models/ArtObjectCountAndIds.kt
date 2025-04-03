package kmp.fbk.kmpartgallery.networking.response_data_models

import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectCountAndIds(
    val total: Int,
    val objectIDs: List<Int>,
)
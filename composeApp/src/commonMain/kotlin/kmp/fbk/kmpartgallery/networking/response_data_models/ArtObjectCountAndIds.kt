package kmp.fbk.kmpartgallery.networking.response_data_models

import kotlinx.serialization.Serializable

/**
 *  A listing of all valid Object IDs available for access.
 */
@Serializable
data class ArtObjectCountAndIds(
    val total: Int,
    val objectIDs: List<Int>,
)
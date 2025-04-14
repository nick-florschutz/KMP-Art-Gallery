package kmp.fbk.kmpartgallery.networking.response_data_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagResponse(
    val term: String,
    @SerialName("AAT_URL") val aATURL: String?,
    @SerialName("Wikidata_URL") val wikidataURL: String?,
)

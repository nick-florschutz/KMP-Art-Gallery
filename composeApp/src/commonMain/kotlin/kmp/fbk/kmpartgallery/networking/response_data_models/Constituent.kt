package kmp.fbk.kmpartgallery.networking.response_data_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Constituent(
    val constituentID: Int,
    val role: String,
    val name: String,
    @SerialName("constituentULAN_URL") val constituentULANURL: String,
    @SerialName("constituentWikidata_URL") val constituentWikidataURL: String,
    val gender: String,
)

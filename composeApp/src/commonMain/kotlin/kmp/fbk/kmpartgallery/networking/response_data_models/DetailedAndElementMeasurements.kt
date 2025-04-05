package kmp.fbk.kmpartgallery.networking.response_data_models

import kmp.fbk.kmpartgallery.networking.JsonIgnoreUnknownKeys
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@SerialName("Measurements")
data class DetailedMeasurements(
    val elementName: String? = null,
    val elementDescription: String? = null,
    val elementMeasurements: ElementMeasurements? = null,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
@SerialName("elementMeasurements")
data class ElementMeasurements(
    @SerialName("Depth") val depth: Double? = null,
    @SerialName("Height") val height: Double? = null,
    @SerialName("Length") val length: Double? = null,
    @SerialName("Width") val width: Double? = null,
    @SerialName("Diameter") val diameter: Double? = null,
)

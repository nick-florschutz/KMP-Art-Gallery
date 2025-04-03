package kmp.fbk.kmpartgallery.networking.response_data_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Measurements")
data class DetailedMeasurements(
    val elementName: String? = null,
    val elementDescription: String? = null,
    val elementMeasurements: ElementMeasurements? = null,
)

@Serializable
@SerialName("elementMeasurements")
data class ElementMeasurements(
    @SerialName("Height") val height: Double? = null,
    @SerialName("Length") val length: Double? = null,
    @SerialName("Width") val width: Double? = null,
)

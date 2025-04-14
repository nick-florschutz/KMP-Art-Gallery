package kmp.fbk.kmpartgallery.networking.response_data_models

import kmp.fbk.kmpartgallery.networking.JsonIgnoreUnknownKeys
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Measurements")
data class DetailedMeasurementsResponse(
    val elementName: String? = null,
    val elementDescription: String? = null,
    val elementMeasurementsResponse: ElementMeasurementsResponse? = null,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
@SerialName("elementMeasurements")
data class ElementMeasurementsResponse(
    @SerialName("Depth") val depth: Double? = null,
    @SerialName("Height") val height: Double? = null,
    @SerialName("Length") val length: Double? = null,
    @SerialName("Width") val width: Double? = null,
    @SerialName("Diameter") val diameter: Double? = null,
)

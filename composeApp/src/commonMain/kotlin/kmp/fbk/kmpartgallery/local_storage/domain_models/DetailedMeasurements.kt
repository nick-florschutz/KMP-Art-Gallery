package kmp.fbk.kmpartgallery.local_storage.domain_models

import kotlinx.serialization.Serializable

@Serializable
data class DetailedMeasurements(
    val elementName: String? = null,
    val elementDescription: String? = null,
    val elementMeasurements: ElementMeasurements? = null,
)

package kmp.fbk.kmpartgallery.domain_models

import kotlinx.serialization.Serializable

@Serializable
data class ElementMeasurements(
    val depth: Double? = null,
    val height: Double? = null,
    val length: Double? = null,
    val width: Double? = null,
    val diameter: Double? = null,
)

package kmp.fbk.kmpartgallery.domain_models

import kotlinx.serialization.Serializable

@Serializable
data class DetailedMeasurements(
    val elementName: String? = null,
    val elementDescription: String? = null,
    val elementMeasurements: ElementMeasurements? = null,
) {
    fun printMeasurements(): String {
        return """
            
            Element Name: ${elementName ?: "--"}
            Element Description: ${elementDescription ?: "--"}
            Element Measurements:
                Width: ${elementMeasurements?.width ?: "--"}
                Height: ${elementMeasurements?.height ?: "--"}
                Depth: ${elementMeasurements?.depth ?: "--"}
                Diameter: ${elementMeasurements?.diameter ?: "--"}
                Length: ${elementMeasurements?.length ?: "--"}
        """.trimIndent()
    }
}

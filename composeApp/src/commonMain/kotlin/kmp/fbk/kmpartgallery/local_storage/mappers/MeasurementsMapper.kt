package kmp.fbk.kmpartgallery.local_storage.mappers

import kmp.fbk.kmpartgallery.local_storage.domain_models.DetailedMeasurements
import kmp.fbk.kmpartgallery.local_storage.domain_models.ElementMeasurements
import kmp.fbk.kmpartgallery.networking.response_data_models.DetailedMeasurementsResponse
import kmp.fbk.kmpartgallery.networking.response_data_models.ElementMeasurementsResponse

fun DetailedMeasurementsResponse.toDetailedMeasurements(): DetailedMeasurements {
    return DetailedMeasurements(
        elementName = elementName,
        elementDescription = elementDescription,
        elementMeasurements = elementMeasurementsResponse?.toElementMeasurements(),
    )
}

fun List<DetailedMeasurementsResponse>.toDetailedMeasurementsList(): List<DetailedMeasurements> {
    return this.map { it.toDetailedMeasurements() }
}

fun ElementMeasurementsResponse.toElementMeasurements(): ElementMeasurements {
    return ElementMeasurements(
        depth = depth,
        height = height,
        length = length,
        width = width,
        diameter = diameter,
    )
}
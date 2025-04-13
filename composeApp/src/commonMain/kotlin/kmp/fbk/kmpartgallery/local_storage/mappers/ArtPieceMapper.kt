package kmp.fbk.kmpartgallery.local_storage.mappers

import kmp.fbk.kmpartgallery.local_storage.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.local_storage.domain_models.ArtPieceTag
import kmp.fbk.kmpartgallery.local_storage.domain_models.Constituent
import kmp.fbk.kmpartgallery.local_storage.domain_models.DetailedMeasurements
import kmp.fbk.kmpartgallery.local_storage.entities.ArtPieceEntity
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtPieceResponse
import kotlinx.serialization.json.Json

fun ArtPieceResponse.toArtPiece(): ArtPiece {
    return ArtPiece(
        localId = null,
        objectID = objectID,
        isHighlight = isHighlight,
        accessionNumber = accessionNumber,
        accessionYear = accessionYear,
        isPublicDomain = isPublicDomain,
        primaryImage = primaryImage,
        primaryImageSmall = primaryImageSmall,
        additionalImages = additionalImages,
        constituentResponses = constituentResponses?.toConstituentList(),
        department = department,
        objectName = objectName,
        title = title,
        culture = culture,
        period = period,
        dynasty = dynasty,
        reign = reign,
        portfolio = portfolio,
        artistRole = artistRole,
        artistPrefix = artistPrefix,
        artistDisplayName = artistDisplayName,
        artistDisplayBio = artistDisplayBio,
        artistSuffix = artistSuffix,
        artistAlphaSort = artistAlphaSort,
        artistNationality = artistNationality,
        artistBeginDate = artistBeginDate,
        artistEndDate = artistEndDate,
        artistGender = artistGender,
        artistWikidataURL = artistWikidataURL,
        artistULANURL = artistULANURL,
        objectDate = objectDate,
        objectBeginDate = objectBeginDate,
        objectEndDate = objectEndDate,
        medium = medium,
        dimensions = dimensions,
        measurements = measurements?.toDetailedMeasurementsList(),
        creditLine = creditLine,
        geographyType = geographyType,
        city = city,
        state = state,
        county = county,
        country = country,
        region = region,
        subregion = subregion,
        locale = locale,
        locus = locus,
        excavation = excavation,
        river = river,
        classification = classification,
        rightsAndReproduction = rightsAndReproduction,
        linkResource = linkResource,
        metadataDate = metadataDate,
        repository = repository,
        objectURL = objectURL,
        tags = tags?.toArtPieceTagList(),
        objectWikidataURL = objectWikidataURL,
        isTimelineWork = isTimelineWork,
        galleryNumber = galleryNumber,
    )
}

fun ArtPiece.toArtPieceEntity(): ArtPieceEntity {
    val json = Json { prettyPrint = true;isLenient = true;ignoreUnknownKeys = true; }
    val additionalImagesString = try {
        json.encodeToString(additionalImages)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
    val constituentResponsesString = try {
        json.encodeToString(constituentResponses)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
    val measurementsString = try {
        json.encodeToString(measurements)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
    val tagsString = try {
        json.encodeToString(tags)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    return ArtPieceEntity(
        localId,
        objectID,
        isHighlight,
        accessionNumber,
        accessionYear,
        isPublicDomain,
        primaryImage,
        primaryImageSmall,
        additionalImagesString,
        department,
        constituentResponsesString,
        objectName,
        title,
        culture,
        period,
        dynasty,
        reign,
        portfolio,
        artistRole,
        artistPrefix,
        artistDisplayName,
        artistDisplayBio,
        artistSuffix,
        artistAlphaSort,
        artistNationality,
        artistBeginDate,
        artistEndDate,
        artistGender,
        artistWikidataURL,
        artistULANURL,
        objectDate,
        objectBeginDate,
        objectEndDate,
        medium,
        dimensions,
        measurementsString,
        creditLine,
        geographyType,
        city,
        state,
        county,
        country,
        region,
        subregion,
        locale,
        locus,
        excavation,
        river,
        classification,
        rightsAndReproduction,
        linkResource,
        metadataDate,
        repository,
        objectURL,
        tagsString,
        objectWikidataURL,
        isTimelineWork,
        galleryNumber
    )
}

fun ArtPieceEntity.toArtPiece(): ArtPiece {
    val json = Json { prettyPrint = true;isLenient = true;ignoreUnknownKeys = true; }
    val additionalImages = try {
        json.decodeFromString<List<String>>(this.additionalImages.orEmpty())
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
    val constituentResponses = try {
        json.decodeFromString<List<Constituent>>(this.constituentResponses.orEmpty())
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
    val measurements = try {
        json.decodeFromString<List<DetailedMeasurements>>(this.measurements.orEmpty())
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
    val tags = try {
        json.decodeFromString<List<ArtPieceTag>>(this.tags.orEmpty())
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    return ArtPiece(
        localId = localId,
        objectID = objectID,
        isHighlight = isHighlight,
        accessionNumber = accessionNumber,
        accessionYear = accessionYear,
        isPublicDomain = isPublicDomain,
        primaryImage = primaryImage,
        primaryImageSmall = primaryImageSmall,
        additionalImages = additionalImages,
        constituentResponses = constituentResponses,
        department = department,
        objectName = objectName,
        title = title,
        culture = culture,
        period = period,
        dynasty = dynasty,
        reign = reign,
        portfolio = portfolio,
        artistRole = artistRole,
        artistPrefix = artistPrefix,
        artistDisplayName = artistDisplayName,
        artistDisplayBio = artistDisplayBio,
        artistSuffix = artistSuffix,
        artistAlphaSort = artistAlphaSort,
        artistNationality = artistNationality,
        artistBeginDate = artistBeginDate,
        artistEndDate = artistEndDate,
        artistGender = artistGender,
        artistWikidataURL = artistWikidataURL,
        artistULANURL = artistULANURL,
        objectDate = objectDate,
        objectBeginDate = objectBeginDate,
        objectEndDate = objectEndDate,
        medium = medium,
        dimensions = dimensions,
        measurements = measurements,
        creditLine = creditLine,
        geographyType = geographyType,
        city = city,
        state = state,
        county = county,
        country = country,
        region = region,
        subregion = subregion,
        locale = locale,
        locus = locus,
        excavation = excavation,
        river = river,
        classification = classification,
        rightsAndReproduction = rightsAndReproduction,
        linkResource = linkResource,
        metadataDate = metadataDate,
        repository = repository,
        objectURL = objectURL,
        tags = tags,
        objectWikidataURL = objectWikidataURL,
        isTimelineWork = isTimelineWork,
        galleryNumber = galleryNumber,
    )
}

fun List<ArtPieceEntity>.toArtPieceList(): List<ArtPiece> {
    return this.map { it.toArtPiece() }
}
package kmp.fbk.kmpartgallery.networking.response_data_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A record for an object, containing all open access data about that object, including its image
 * (if the image is available under Open Access).
 * @param objectID The unique identifier for this object.
 * @param isHighlight Whether or not this object is a popular and important artwork in the collection.
 * @param accessionNumber The identifying number for each artwork (not always unique)
 * @param accessionYear The year the artwork was acquired.
 * @param isPublicDomain Whether or not this object is in the public domain.
 * @param primaryImage The URL for the primary image of this object. (JPEG Format)
 * @param primaryImageSmall URL to the lower-res primary image of an object. (JPEG format)
 * @param additionalImages A list of additional images for this object. (JPEG Format)
 * @param constituents A list of constituents for this object.
 * @param department The department this object belongs to.
 * @param objectName Describes the physical type of the object (Dress", "Painting", "Photograph", or "Vase")
 * @param title Title, identifying phrase, or name given to a work of art.
 * @param culture Information about the culture, or people from which an object was created.
 * @param period Time or time period when an object was created ("Ming dynasty (1368-1644)", "Middle Bronze Age")
 * @param dynasty Dynasty (a succession of rulers of the same line or family) under which an object was created
 * @param reign Reign of a monarch or ruler under which an object was created
 * @param portfolio A set of works created as a group or published as a series.
 * @param artistRole The role of the artist in the object. ("Artist for Painting", "Designer for Dress")
 * @param artistPrefix Describes the extent of creation or describes an attribution qualifier to the information given in the artistRole field ("In the Style of", "Possibly by", "Written in French by")
 * @param artistDisplayName Artist name in the correct order for display (Vincent Van Gogh)
 * @param artistDisplayBio Nationality and life dates of an artist, also includes birth and death city when known. ("Dutch, Zundert 1853–1890 Auvers-sur-Oise")
 * @param artistSuffix
 * @param artistAlphaSort Used to sort artist names alphabetically. Last Name, First Name, Middle Name, Suffix, and Honorific fields, in that order. ("Gogh, Vincent van")
 * @param artistNationality
 * @param artistBeginDate
 * @param artistEndDate
 * @param artistGender
 * @param artistWikidataURL
 * @param artistULANURL
 * @param objectDate
 * @param objectBeginDate
 * @param objectEndDate
 * @param medium
 * @param dimensions
 * @param measurements
 * @param creditLine
 * @param geographyType
 * @param city
 * @param state
 * @param county
 * @param country
 * @param region
 * @param subregion
 * @param locale
 * @param locus
 * @param excavation
 * @param river
 * @param classification
 * @param rightsAndReproduction
 * @param linkResource
 * @param metadataDate
 * @param repository
 * @param objectURL
 * @param tags
 * @param objectWikidataURL
 * @param isTimelineWork
 * @param galleryNumber
 */
@Serializable
data class ArtPieceResponse(
    val objectID: Int,
    val isHighlight: Boolean,
    val accessionNumber: String?,
    val accessionYear: String?,
    val isPublicDomain: Boolean,
    val primaryImage: String,
    val primaryImageSmall: String,
    val additionalImages: List<String>?,
    val constituents: List<Constituent>?,
    val department: String?,
    val objectName: String?,
    val title: String?,
    val culture: String?,
    val period: String?,
    val dynasty: String?,
    val reign: String?,
    val portfolio: String?,
    val artistRole: String?,
    val artistPrefix: String?,
    val artistDisplayName: String?,
    val artistDisplayBio: String?,
    val artistSuffix: String?,
    val artistAlphaSort: String?,
    val artistNationality: String?,
    val artistBeginDate: String?,
    val artistEndDate: String?,
    val artistGender: String?,
    @SerialName("artistWikidata_URL") val artistWikidataURL: String?,
    @SerialName("artistULAN_URL") val artistULANURL: String?,
    val objectDate: String?,
    val objectBeginDate: Int?,
    val objectEndDate: Int?,
    val medium: String?,
    val dimensions: String?,
    val measurements: List<DetailedMeasurements>?,
    val creditLine: String?,
    val geographyType: String?,
    val city: String?,
    val state: String?,
    val county: String?,
    val country: String?,
    val region: String?,
    val subregion: String?,
    val locale: String?,
    val locus: String?,
    val excavation: String?,
    val river: String?,
    val classification: String?,
    val rightsAndReproduction: String?,
    val linkResource: String?,
    val metadataDate: String?,
    val repository: String?,
    val objectURL: String?,
    val tags: List<TagResponse>?,
    @SerialName("objectWikidata_URL") val objectWikidataURL: String?,
    val isTimelineWork: Boolean?,
    @SerialName("GalleryNumber") val galleryNumber: String?,
)

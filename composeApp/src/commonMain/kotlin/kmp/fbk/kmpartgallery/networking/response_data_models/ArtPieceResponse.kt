package kmp.fbk.kmpartgallery.networking.response_data_models

import kmp.fbk.kmpartgallery.networking.JsonIgnoreUnknownKeys
import kotlinx.serialization.ExperimentalSerializationApi
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
@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class ArtPieceResponse(
    val objectID: Int? = null,
    val isHighlight: Boolean? = null,
    val accessionNumber: String? = null,
    val accessionYear: String? = null,
    val isPublicDomain: Boolean? = null,
    val primaryImage: String? = null,
    val primaryImageSmall: String? = null,
    val additionalImages: List<String>? = null,
    val constituents: List<Constituent>? = null,
    val department: String? = null,
    val objectName: String? = null,
    val title: String? = null,
    val culture: String? = null,
    val period: String? = null,
    val dynasty: String? = null,
    val reign: String? = null,
    val portfolio: String? = null,
    val artistRole: String? = null,
    val artistPrefix: String? = null,
    val artistDisplayName: String? = null,
    val artistDisplayBio: String? = null,
    val artistSuffix: String? = null,
    val artistAlphaSort: String? = null,
    val artistNationality: String? = null,
    val artistBeginDate: String? = null,
    val artistEndDate: String? = null,
    val artistGender: String? = null,
    @SerialName("artistWikidata_URL") val artistWikidataURL: String? = null,
    @SerialName("artistULAN_URL") val artistULANURL: String? = null,
    val objectDate: String? = null,
    val objectBeginDate: Int? = null,
    val objectEndDate: Int? = null,
    val medium: String? = null,
    val dimensions: String? = null,
    val measurements: List<DetailedMeasurements>? = null,
    val creditLine: String? = null,
    val geographyType: String? = null,
    val city: String? = null,
    val state: String? = null,
    val county: String? = null,
    val country: String? = null,
    val region: String? = null,
    val subregion: String? = null,
    val locale: String? = null,
    val locus: String? = null,
    val excavation: String? = null,
    val river: String? = null,
    val classification: String? = null,
    val rightsAndReproduction: String? = null,
    val linkResource: String? = null,
    val metadataDate: String? = null,
    val repository: String? = null,
    val objectURL: String? = null,
    val tags: List<TagResponse>? = null,
    @SerialName("objectWikidata_URL") val objectWikidataURL: String? = null,
    val isTimelineWork: Boolean? = null,
    @SerialName("GalleryNumber") val galleryNumber: String? = null,
)

package kmp.fbk.kmpartgallery.local_storage.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kmp.fbk.kmpartgallery.domain_models.ArtPieceTag

@Entity(
    tableName = ArtPieceEntity.TABLE_NAME,
)
data class ArtPieceEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = IEntity.Companion.Column.LOCAL_ID) override val localId: Long? = null,
    @ColumnInfo(name = Column.OBJECT_ID) val objectID: Int? = null,
    @ColumnInfo(name = Column.IS_HIGHLIGHT) val isHighlight: Boolean? = null,
    @ColumnInfo(name = Column.ACCESSION_NUMBER) val accessionNumber: String? = null,
    @ColumnInfo(name = Column.ACCESSION_YEAR) val accessionYear: String? = null,
    @ColumnInfo(name = Column.IS_PUBLIC_DOMAIN) val isPublicDomain: Boolean? = null,
    @ColumnInfo(name = Column.PRIMARY_IMAGE) val primaryImage: String? = null,
    @ColumnInfo(name = Column.PRIMARY_IMAGE_SMALL) val primaryImageSmall: String? = null,
    @ColumnInfo(name = Column.ADDITIONAL_IMAGES) val additionalImages: String? = null,
    @ColumnInfo(name = Column.DEPARTMENT) val department: String? = null,
    @ColumnInfo(name = Column.CONSTITUENTS) val constituentResponses: String? = null,
    @ColumnInfo(name = Column.OBJECT_NAME) val objectName: String? = null,
    @ColumnInfo(name = Column.TITLE) val title: String? = null,
    @ColumnInfo(name = Column.CULTURE) val culture: String? = null,
    @ColumnInfo(name = Column.PERIOD) val period: String? = null,
    @ColumnInfo(name = Column.DYNAMACY) val dynasty: String? = null,
    @ColumnInfo(name = Column.REIGN) val reign: String? = null,
    @ColumnInfo(name = Column.PORTFOLIO) val portfolio: String? = null,
    @ColumnInfo(name = Column.ARTIST_ROLE) val artistRole: String? = null,
    @ColumnInfo(name = Column.ARTIST_PREFIX) val artistPrefix: String? = null,
    @ColumnInfo(name = Column.ARTIST_DISPLAY_NAME) val artistDisplayName: String? = null,
    @ColumnInfo(name = Column.ARTIST_DISPLAY_BIO) val artistDisplayBio: String? = null,
    @ColumnInfo(name = Column.ARTIST_SUFFIX) val artistSuffix: String? = null,
    @ColumnInfo(name = Column.ARTIST_ALPHA_SORT) val artistAlphaSort: String? = null,
    @ColumnInfo(name = Column.ARTIST_NATIONALITY) val artistNationality: String? = null,
    @ColumnInfo(name = Column.ARTIST_BEGIN_DATE) val artistBeginDate: String? = null,
    @ColumnInfo(name = Column.ARTIST_END_DATE) val artistEndDate: String? = null,
    @ColumnInfo(name = Column.ARTIST_GENDER) val artistGender: String? = null,
    @ColumnInfo(name = Column.ARTIST_WIKIDATA_URL) val artistWikidataURL: String? = null,
    @ColumnInfo(name = Column.ARTIST_ULAN_URL) val artistULANURL: String? = null,
    @ColumnInfo(name = Column.OBJECT_DATE) val objectDate: String? = null,
    @ColumnInfo(name = Column.OBJECT_BEGIN_DATE) val objectBeginDate: Int? = null,
    @ColumnInfo(name = Column.OBJECT_END_DATE) val objectEndDate: Int? = null,
    @ColumnInfo(name = Column.MEDIUM) val medium: String? = null,
    @ColumnInfo(name = Column.DIMENSIONS) val dimensions: String? = null,
    @ColumnInfo(name = Column.MEASUREMENTS) val measurements: String? = null,
    @ColumnInfo(name = Column.CREDIT_LINE) val creditLine: String? = null,
    @ColumnInfo(name = Column.GEOGRAPHY_TYPE) val geographyType: String? = null,
    @ColumnInfo(name = Column.CITY) val city: String? = null,
    @ColumnInfo(name = Column.STATE) val state: String? = null,
    @ColumnInfo(name = Column.COUNTY) val county: String? = null,
    @ColumnInfo(name = Column.COUNTRY) val country: String? = null,
    @ColumnInfo(name = Column.REGION) val region: String? = null,
    @ColumnInfo(name = Column.SUBREGION) val subregion: String? = null,
    @ColumnInfo(name = Column.LOCALE) val locale: String? = null,
    @ColumnInfo(name = Column.LOCUS) val locus: String? = null,
    @ColumnInfo(name = Column.EXCAVATION) val excavation: String? = null,
    @ColumnInfo(name = Column.RIVER) val river: String? = null,
    @ColumnInfo(name = Column.CLASSIFICATION) val classification: String? = null,
    @ColumnInfo(name = Column.RIGHTS_AND_REPRODUCTION) val rightsAndReproduction: String? = null,
    @ColumnInfo(name = Column.LINK_RESOURCE) val linkResource: String? = null,
    @ColumnInfo(name = Column.METADATA_DATE) val metadataDate: String? = null,
    @ColumnInfo(name = Column.REPOSITORY) val repository: String? = null,
    @ColumnInfo(name = Column.OBJECT_URL) val objectURL: String? = null,
    @ColumnInfo(name = Column.TAGS) val tags: String? = null,
    @ColumnInfo(name = Column.OBJECT_WIKIDATA_URL) val objectWikidataURL: String? = null,
    @ColumnInfo(name = Column.IS_TIMELINE_WORK) val isTimelineWork: Boolean? = null,
    @ColumnInfo(name = Column.GALLERY_NUMBER) val galleryNumber: String? = null,
): IEntity {
    companion object {
        const val TABLE_NAME = "art_piece_entity"
        object Column {
            const val OBJECT_ID = "object_id"
            const val IS_HIGHLIGHT = "is_highlight"
            const val ACCESSION_NUMBER = "accession_number"
            const val ACCESSION_YEAR = "accession_year"
            const val IS_PUBLIC_DOMAIN = "is_public_domain"
            const val PRIMARY_IMAGE = "primary_image"
            const val PRIMARY_IMAGE_SMALL = "primary_image_small"
            const val ADDITIONAL_IMAGES = "additional_images"
            const val CONSTITUENTS = "constituents"
            const val DEPARTMENT = "department"
            const val OBJECT_NAME = "object_name"
            const val TITLE = "title"
            const val CULTURE = "culture"
            const val PERIOD = "period"
            const val DYNAMACY = "dynasty"
            const val REIGN = "reign"
            const val PORTFOLIO = "portfolio"
            const val ARTIST_ROLE = "artist_role"
            const val ARTIST_PREFIX = "artist_prefix"
            const val ARTIST_DISPLAY_NAME = "artist_display_name"
            const val ARTIST_DISPLAY_BIO = "artist_display_bio"
            const val ARTIST_SUFFIX = "artist_suffix"
            const val ARTIST_ALPHA_SORT = "artist_alpha_sort"
            const val ARTIST_NATIONALITY = "artist_nationality"
            const val ARTIST_BEGIN_DATE = "artist_begin_date"
            const val ARTIST_END_DATE = "artist_end_date"
            const val ARTIST_GENDER = "artist_gender"
            const val ARTIST_WIKIDATA_URL = "artist_wikidata_url"
            const val ARTIST_ULAN_URL = "artist_ulan_url"
            const val OBJECT_DATE = "object_date"
            const val OBJECT_BEGIN_DATE = "object_begin_date"
            const val OBJECT_END_DATE = "object_end_date"
            const val MEDIUM = "medium"
            const val DIMENSIONS = "dimensions"
            const val MEASUREMENTS = "measurements"
            const val CREDIT_LINE = "credit_line"
            const val GEOGRAPHY_TYPE = "geography_type"
            const val CITY = "city"
            const val STATE = "state"
            const val COUNTY = "county"
            const val COUNTRY = "country"
            const val REGION = "region"
            const val SUBREGION = "subregion"
            const val LOCALE = "locale"
            const val LOCUS = "locus"
            const val EXCAVATION = "excavation"
            const val RIVER = "river"
            const val CLASSIFICATION = "classification"
            const val RIGHTS_AND_REPRODUCTION = "rights_and_reproduction"
            const val LINK_RESOURCE = "link_resource"
            const val METADATA_DATE = "metadata_date"
            const val REPOSITORY = "repository"
            const val OBJECT_URL = "object_url"
            const val TAGS = "tags"
            const val OBJECT_WIKIDATA_URL = "object_wikidata_url"
            const val IS_TIMELINE_WORK = "is_timeline_work"
            const val GALLERY_NUMBER = "gallery_number"
        }
    }
}

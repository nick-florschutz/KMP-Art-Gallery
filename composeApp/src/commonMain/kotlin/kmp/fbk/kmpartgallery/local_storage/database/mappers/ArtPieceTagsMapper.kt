package kmp.fbk.kmpartgallery.local_storage.database.mappers

import kmp.fbk.kmpartgallery.domain_models.ArtPieceTag
import kmp.fbk.kmpartgallery.networking.response_data_models.TagResponse

fun TagResponse.toArtPieceTag(): ArtPieceTag {
    return ArtPieceTag(
        term = term,
        aATURL = aATURL,
        wikidataURL = wikidataURL,
    )
}

fun List<TagResponse>.toArtPieceTagList(): List<ArtPieceTag> {
    return this.map { it.toArtPieceTag() }
}
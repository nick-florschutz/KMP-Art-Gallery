package kmp.fbk.kmpartgallery.local_storage.database.mappers

import kmp.fbk.kmpartgallery.domain_models.Constituent
import kmp.fbk.kmpartgallery.networking.response_data_models.ConstituentResponse

fun ConstituentResponse.toConstituent(): Constituent {
    return Constituent(
        constituentID = constituentID,
        role = role,
        name = name,
        constituentULANURL = constituentULANURL,
        constituentWikidataURL = constituentWikidataURL,
    )
}

fun List<ConstituentResponse>.toConstituentList(): List<Constituent> {
    return this.map { it.toConstituent() }
}
package kmp.fbk.kmpartgallery.networking

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.CallConverterFactory

object NetworkRequestBuilder {
    private const val MET_ART_MUSEUM_BASE_URL = "https://collectionapi.metmuseum.org/"

    val requestBuilder = Ktorfit.Builder()
        .converterFactories(CallConverterFactory())
        .baseUrl(MET_ART_MUSEUM_BASE_URL)
        .build()
}
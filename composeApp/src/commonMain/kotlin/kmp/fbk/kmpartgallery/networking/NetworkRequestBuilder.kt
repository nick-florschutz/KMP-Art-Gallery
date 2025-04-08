package kmp.fbk.kmpartgallery.networking

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.CallConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkRequestBuilder {
    private const val MET_ART_MUSEUM_BASE_URL = "https://collectionapi.metmuseum.org/"

    val requestBuilder = Ktorfit.Builder()
        .converterFactories(CallConverterFactory())
        .httpClient(
            HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    )
                }
            }
        )
        .baseUrl(MET_ART_MUSEUM_BASE_URL)
        .build()
}
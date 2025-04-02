package kmp.fbk.kmpartgallery

import de.jensklingenberg.ktorfit.Call
import de.jensklingenberg.ktorfit.Callback
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.CallConverterFactory
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.GET
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder

interface MetArtApi {
    @GET("public/collection/v1/objects")
    suspend fun getAllObjectIds(): String

    @GET("public/collection/v1/objects")
    suspend fun getAllObjectIdsCall(): Call<String>
}

@Serializable
data class ArtObjectCountAndIds(
    val total: Int,
    val objectIDs: List<Int>,
)

object RequestBuilder {
    private val requestBuilder = Ktorfit.Builder()
        .converterFactories(CallConverterFactory())
        .baseUrl("https://collectionapi.metmuseum.org/")
        .build()

    suspend fun getAllObjectIds(
        metArtApi: MetArtApi = requestBuilder.createMetArtApi(),
    ): ArtObjectCountAndIds {
        return Json.decodeFromString<ArtObjectCountAndIds>(
            metArtApi.getAllObjectIds()
        )
    }

    suspend fun getAllObjectIdsCall(
        metArtApi: MetArtApi = requestBuilder.createMetArtApi(),
    ) {
        metArtApi.getAllObjectIdsCall().onExecute(
            callBack = object: Callback<String> {
                override fun onError(exception: Throwable) {
                    exception.printStackTrace()
                    throw exception
                }

                override fun onResponse(call: String, response: HttpResponse) {
                    Napier.i(tag = "RequestBuilder.getAllObjectIdsCall()", message = "response: $response")
                    Napier.i(tag = "RequestBuilder.getAllObjectIdsCall()") {
                        val data = Json.decodeFromString<ArtObjectCountAndIds>(call)
                        "data: $data"
                    }
                }
            },
        )
    }
}
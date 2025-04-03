package kmp.fbk.kmpartgallery.networking

import de.jensklingenberg.ktorfit.Callback
import io.github.aakira.napier.Napier
import io.ktor.client.statement.HttpResponse
import kmp.fbk.kmpartgallery.createMetArtApi
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtObjectCountAndIds
import kotlinx.serialization.json.Json

class MetArtMuseumApiRequests(
    private val metArtApi: MetArtApi = NetworkRequestBuilder.requestBuilder.createMetArtApi(),
) {
    suspend fun getAllObjectIds(): ArtObjectCountAndIds {
        return Json.decodeFromString<ArtObjectCountAndIds>(
            metArtApi.getAllObjectIds()
        )
    }

    // Example using Callback functionality.
    // TODO: Use something like this for fetching and storing data
    suspend fun fetchAllObjectIdsCall() {
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
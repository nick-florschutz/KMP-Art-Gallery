package kmp.fbk.kmpartgallery.networking

import de.jensklingenberg.ktorfit.Callback
import io.github.aakira.napier.Napier
import io.ktor.client.statement.HttpResponse
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtObjectCountAndIds
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtPieceResponse
import kmp.fbk.kmpartgallery.networking.response_data_models.DepartmentResponse
import kmp.fbk.kmpartgallery.networking.response_data_models.DepartmentsResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class MetArtMuseumApiRequests(
    private val metArtApi: MetArtApi = NetworkRequestBuilder.requestBuilder.createMetArtApi(),
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    suspend fun getAllObjectIds(): ArtObjectCountAndIds {
        return json.decodeFromString<ArtObjectCountAndIds>(
            metArtApi.getAllArtPiecesIds()
        )
    }

    suspend fun getArtPieceById(objectID: Int): ArtPieceResponse {
        return json.decodeFromString<ArtPieceResponse>(
            metArtApi.getArtPieceById(objectID)
        )
    }

    suspend fun getAllDepartments(): List<DepartmentResponse> {
        return json.decodeFromString<DepartmentsResponse>(
            metArtApi.getAllDepartments()
        ).departments
    }

    // Example using Callback functionality.
    // TODO: Use something like this for fetching and storing data
    suspend fun fetchAllObjectIdsCall() {
        metArtApi.getAllArtPiecesIdsCall().onExecute(
            callBack = object: Callback<String> {
                override fun onError(exception: Throwable) {
                    exception.printStackTrace()
                    throw exception
                }

                override fun onResponse(call: String, response: HttpResponse) {
                    Napier.i(tag = "RequestBuilder.getAllObjectIdsCall()", message = "response: $response")
                    Napier.i(tag = "RequestBuilder.getAllObjectIdsCall()") {
                        val data = json.decodeFromString<ArtObjectCountAndIds>(call)
                        "data: $data"
                    }
                }
            },
        )
    }
}
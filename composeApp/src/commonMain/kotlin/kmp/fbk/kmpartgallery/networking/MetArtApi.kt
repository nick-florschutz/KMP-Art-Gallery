package kmp.fbk.kmpartgallery.networking

import de.jensklingenberg.ktorfit.Call
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface MetArtApi {
    @GET("public/collection/v1/objects")
    suspend fun getAllArtPiecesIds(): String

    @GET("public/collection/v1/objects")
    suspend fun getAllArtPiecesIdsCall(): Call<String>

    @GET("public/collection/v1/objects/{objectID}")
    suspend fun getArtPieceById(
        @Path("objectID") objectID: Int
    ): String
}
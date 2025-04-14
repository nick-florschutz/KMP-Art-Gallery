package kmp.fbk.kmpartgallery.networking

import de.jensklingenberg.ktorfit.Call
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtObjectCountAndIds
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtPieceResponse
import kmp.fbk.kmpartgallery.networking.response_data_models.DepartmentsResponse

interface MetArtApi {
    @GET("public/collection/v1/objects")
    suspend fun getAllArtPiecesIds(): ArtObjectCountAndIds

    @GET("public/collection/v1/objects")
    suspend fun getAllArtPiecesIdsCall(): Call<ArtObjectCountAndIds>

    @GET("public/collection/v1/objects/{objectID}")
    suspend fun getArtPieceById(
        @Path("objectID") objectID: Int
    ): ArtPieceResponse

    @GET("public/collection/v1/departments")
    suspend fun getAllDepartments(): DepartmentsResponse

    @GET("public/collection/v1/departments")
    fun getAllDepartmentsCall(): Call<DepartmentsResponse>

}
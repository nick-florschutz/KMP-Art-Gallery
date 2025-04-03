package kmp.fbk.kmpartgallery.networking

import de.jensklingenberg.ktorfit.Call
import de.jensklingenberg.ktorfit.http.GET

interface MetArtApi {
    @GET("public/collection/v1/objects")
    suspend fun getAllObjectIds(): String

    @GET("public/collection/v1/objects")
    suspend fun getAllObjectIdsCall(): Call<String>
}
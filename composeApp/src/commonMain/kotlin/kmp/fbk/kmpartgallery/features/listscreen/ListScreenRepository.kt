package kmp.fbk.kmpartgallery.features.listscreen

import kmp.fbk.kmpartgallery.networking.MetArtMuseumApiRequests

class ListScreenRepository(
    private val metArtMuseumApiRequests: MetArtMuseumApiRequests = MetArtMuseumApiRequests(),
) {
    suspend fun getAllObjectIds() = metArtMuseumApiRequests.getAllObjectIds()

    suspend fun getArtPieceById(objectID: Int) = metArtMuseumApiRequests.getArtPieceById(objectID)

    suspend fun getAllDepartments() = metArtMuseumApiRequests.getAllDepartments()
}
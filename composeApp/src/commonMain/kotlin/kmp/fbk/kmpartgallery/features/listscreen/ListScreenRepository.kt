package kmp.fbk.kmpartgallery.features.listscreen

import de.jensklingenberg.ktorfit.Callback
import io.github.aakira.napier.Napier
import io.ktor.client.statement.HttpResponse
import kmp.fbk.kmpartgallery.local_storage.dao.ArtPieceDao
import kmp.fbk.kmpartgallery.local_storage.dao.DepartmentDao
import kmp.fbk.kmpartgallery.local_storage.domain_models.Department
import kmp.fbk.kmpartgallery.local_storage.mappers.toDepartmentEntity
import kmp.fbk.kmpartgallery.local_storage.mappers.toDepartmentsList
import kmp.fbk.kmpartgallery.networking.MetArtApi
import kmp.fbk.kmpartgallery.networking.NetworkRequestBuilder
import kmp.fbk.kmpartgallery.networking.createMetArtApi
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtObjectCountAndIds
import org.koin.mp.KoinPlatform

class ListScreenRepository(
    private val metArtApi: MetArtApi = NetworkRequestBuilder.requestBuilder.createMetArtApi(),
    private val departmentDao: DepartmentDao,
    private val artPieceDao: ArtPieceDao,
) {
    suspend fun getAllObjectIds() = metArtApi.getAllArtPiecesIds()

    suspend fun getArtPieceById(objectID: Int) = metArtApi.getArtPieceById(objectID)

    suspend fun getAllDepartments() = metArtApi.getAllDepartments().departments

    suspend fun getAllDepartmentsFromDb() = departmentDao.getAllDepartments().toDepartmentsList()

    suspend fun insertDepartmentIntoDb(department: Department) {
        val departmentEntity = department.toDepartmentEntity()
        departmentDao.insert(departmentEntity)
    }

    suspend fun getAllArtPiecesFromDb() = artPieceDao.getAllArtPieces()

    fun getAllArtPiecesFromDbFlow() = artPieceDao.getAllArtPiecesFlow()

    suspend fun fetchAllObjectIdsCall() {
        metArtApi.getAllArtPiecesIdsCall().onExecute(
            callBack = object: Callback<ArtObjectCountAndIds> {
                override fun onError(exception: Throwable) {
                    exception.printStackTrace()
                    throw exception
                }

                override fun onResponse(call: ArtObjectCountAndIds, response: HttpResponse) {
                    Napier.i(tag = "RequestBuilder.getAllObjectIdsCall()", message = "response: $response")
                    Napier.i(tag = "RequestBuilder.getAllObjectIdsCall()") {
                        "data: $call"
                    }
                }
            },
        )
    }

}
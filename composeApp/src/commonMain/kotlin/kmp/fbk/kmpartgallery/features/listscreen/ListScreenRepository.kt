package kmp.fbk.kmpartgallery.features.listscreen

import de.jensklingenberg.ktorfit.Callback
import io.github.aakira.napier.Napier
import io.ktor.client.statement.HttpResponse
import kmp.fbk.kmpartgallery.local_storage.database.dao.ArtPieceDao
import kmp.fbk.kmpartgallery.local_storage.database.dao.DepartmentDao
import kmp.fbk.kmpartgallery.domain_models.Department
import kmp.fbk.kmpartgallery.local_storage.database.mappers.toDepartmentEntity
import kmp.fbk.kmpartgallery.local_storage.database.mappers.toDepartmentsList
import kmp.fbk.kmpartgallery.networking.MetArtApi
import kmp.fbk.kmpartgallery.networking.NetworkRequestBuilder
import kmp.fbk.kmpartgallery.networking.createMetArtApi
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtObjectCountAndIds
import kotlinx.coroutines.flow.map
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

    fun getAllDepartmentsFromDbFlow() = departmentDao.getAllDepartmentsFlow()
        .map { it.toDepartmentsList() }

    suspend fun insertDepartmentIntoDb(department: Department) {
        val departmentEntity = department.toDepartmentEntity()
        departmentDao.insert(departmentEntity)
    }

    suspend fun getAllArtPiecesFromDb() = artPieceDao.getAllArtPieces()

    fun getAllArtPiecesFromDbFlow() = artPieceDao.getAllArtPiecesFlow()

    suspend fun getFiveArtPiecePrimaryImages() = artPieceDao.getFiveArtPiecePrimaryImages()

    suspend fun getFiveArtPiecePrimaryImagesFlow() = artPieceDao.getFiveArtPiecePrimaryImagesFlow()

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
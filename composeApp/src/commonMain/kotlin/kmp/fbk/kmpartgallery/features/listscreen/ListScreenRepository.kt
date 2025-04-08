package kmp.fbk.kmpartgallery.features.listscreen

import kmp.fbk.kmpartgallery.local_storage.dao.DepartmentDao
import kmp.fbk.kmpartgallery.local_storage.domain_models.Department
import kmp.fbk.kmpartgallery.local_storage.mappers.toDepartmentEntity
import kmp.fbk.kmpartgallery.local_storage.mappers.toDepartmentsList
import kmp.fbk.kmpartgallery.networking.MetArtApi
import kmp.fbk.kmpartgallery.networking.NetworkRequestBuilder
import kmp.fbk.kmpartgallery.networking.createMetArtApi
import org.koin.mp.KoinPlatform

class ListScreenRepository(
    private val metArtApi: MetArtApi = NetworkRequestBuilder.requestBuilder.createMetArtApi(),
    private val departmentDao: DepartmentDao,
) {
    suspend fun getAllObjectIds() = metArtApi.getAllArtPiecesIds()

    suspend fun getArtPieceById(objectID: Int) = metArtApi.getArtPieceById(objectID)

    suspend fun getAllDepartments() = metArtApi.getAllDepartments().departments

    suspend fun getAllDepartmentsFromDb() = departmentDao.getAllDepartments().toDepartmentsList()

    suspend fun insertDepartmentIntoDb(department: Department) {
        val departmentEntity = department.toDepartmentEntity()
        departmentDao.insert(departmentEntity)
    }


//    suspend fun fetchAllObjectIdsCall() {
//        metArtApi.getAllArtPiecesIdsCall().onExecute(
//            callBack = object: Callback<ArtObjectCountAndIds> {
//                override fun onError(exception: Throwable) {
//                    exception.printStackTrace()
//                    throw exception
//                }
//
//                override fun onResponse(call: ArtObjectCountAndIds, response: HttpResponse) {
//                    Napier.i(tag = "RequestBuilder.getAllObjectIdsCall()", message = "response: $response")
//                    Napier.i(tag = "RequestBuilder.getAllObjectIdsCall()") {
//                        val data = json.decodeFromString<ArtObjectCountAndIds>(call)
//                        "data: $data"
//                    }
//                }
//            },
//        )
//    }


}
package kmp.fbk.kmpartgallery.features.listscreen

import kmp.fbk.kmpartgallery.local_storage.dao.DepartmentDao
import kmp.fbk.kmpartgallery.local_storage.domain_models.Department
import kmp.fbk.kmpartgallery.local_storage.mappers.toDepartmentEntity
import kmp.fbk.kmpartgallery.networking.MetArtMuseumApiRequests
import org.koin.mp.KoinPlatform

class ListScreenRepository(
    private val metArtMuseumApiRequests: MetArtMuseumApiRequests = MetArtMuseumApiRequests(),
    private val departmentDao: DepartmentDao,
) {
    suspend fun getAllObjectIds() = metArtMuseumApiRequests.getAllObjectIds()

    suspend fun getArtPieceById(objectID: Int) = metArtMuseumApiRequests.getArtPieceById(objectID)

    suspend fun getAllDepartments() = metArtMuseumApiRequests.getAllDepartments()

    suspend fun getAllDepartmentsFromDb() = departmentDao.getAllDepartments()

    suspend fun insertDepartmentIntoDb(department: Department) {
        val departmentEntity = department.toDepartmentEntity()
        departmentDao.insert(departmentEntity)
    }

}
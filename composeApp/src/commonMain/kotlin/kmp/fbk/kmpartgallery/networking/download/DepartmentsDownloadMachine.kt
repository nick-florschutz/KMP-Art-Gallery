package kmp.fbk.kmpartgallery.networking.download

import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.local_storage.dao.DepartmentDao
import kmp.fbk.kmpartgallery.local_storage.mappers.toDepartmentEntityList
import kmp.fbk.kmpartgallery.networking.MetArtApi
import kmp.fbk.kmpartgallery.networking.NetworkRequestBuilder
import kmp.fbk.kmpartgallery.networking.createMetArtApi
import kmp.fbk.kmpartgallery.snackbar.AppSnackBarBannerHostState
import kmp.fbk.kmpartgallery.snackbar.BannerInformation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DepartmentsDownloadMachine(
    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val departmentDao: DepartmentDao,
) : DownloadMachine() {

    fun downloadDepartments() {
        downloadItem(
            apiCall = metArtApi.getAllDepartmentsCall(),
            onSuccessfulDownload = { departmentsResponse ->
                coroutineScope.launch {
                    val departmentResponseToEntity = departmentsResponse.departments
                        .toDepartmentEntityList()
                        .toSet()

                    val localDepartmentsDepartmentIdList = departmentDao.getAllDepartments().map {
                        it.departmentId
                    }.toSet()

                    // Take only what needs to be downloaded
                    val departmentsToDownload = departmentResponseToEntity.filterNot {
                        localDepartmentsDepartmentIdList.contains(it.departmentId)
                    }

                    departmentsToDownload.forEach { department ->
                        departmentDao.insert(department)
                    }

                    Napier.i(tag = this@DepartmentsDownloadMachine::class.simpleName) {
                        "Downloaded ${departmentsToDownload.size} departments Successfully"
                    }
                }
            },
            onError = { error ->
                error.printStackTrace()
                Napier.e(tag = this@DepartmentsDownloadMachine::class.simpleName) {
                    "Error downloading Departments: ${error.message}"
                }
            },
        )
    }
}
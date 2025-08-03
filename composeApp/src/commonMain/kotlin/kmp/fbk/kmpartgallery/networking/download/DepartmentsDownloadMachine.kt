package kmp.fbk.kmpartgallery.networking.download

import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.local_storage.database.dao.DepartmentDao
import kmp.fbk.kmpartgallery.local_storage.database.mappers.toDepartmentEntityList
import kmp.fbk.kmpartgallery.local_storage.preferences.DataStoreRepository
import kmp.fbk.kmpartgallery.networking.MetArtApi
import kmp.fbk.kmpartgallery.networking.NetworkRequestBuilder
import kmp.fbk.kmpartgallery.networking.createMetArtApi
import kmp.fbk.kmpartgallery.networking.download.ArtPieceDownloadMachine.Companion
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.snackbar.AppSnackBarBannerHostState
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.snackbar.BannerInformation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds

class DepartmentsDownloadMachine(
    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val departmentDao: DepartmentDao,
    private val dataStoreRepository: DataStoreRepository,
) : DownloadMachine() {

    companion object {
        private const val LAST_DOWNLOAD_TIME_DATASTORE_KEY = "departments_last_download_time_datastore_key"
        private val DOWNLOAD_TIME_INTERVAL = 1.days
    }

    fun downloadDepartments() {
        downloadItem(
            apiCall = metArtApi.getAllDepartmentsCall(),
            onSuccessfulDownload = { departmentsResponse ->
                val now = Clock.System.now()
                val startTime = now.toEpochMilliseconds()

                coroutineScope.launch {
                    val lastDownloadTimestamp = dataStoreRepository.readLongValue(
                        LAST_DOWNLOAD_TIME_DATASTORE_KEY
                    ) ?: 0L
                    Napier.i(tag = this@DepartmentsDownloadMachine::class.simpleName) {
                        "Departments Last Download Timestamp: $lastDownloadTimestamp"
                    }

                    if (startTime.minus(lastDownloadTimestamp) < DOWNLOAD_TIME_INTERVAL.inWholeMilliseconds) {
                        Napier.i(tag = this@DepartmentsDownloadMachine::class.simpleName) {
                            "Not downloading departments. Time remaining until next download: ${
                                (lastDownloadTimestamp - (startTime.minus(DOWNLOAD_TIME_INTERVAL.inWholeMilliseconds))).milliseconds
                            }"
                        }
                        return@launch
                    }

                    val departmentResponseToEntity = departmentsResponse.departments
                        .toDepartmentEntityList()
                        .toSet()

                    val localDepartmentsDepartmentIdList = departmentDao.getAllDepartments().map {
                        it.departmentId
                    }.toSet()

                    if (localDepartmentsDepartmentIdList.containsAll(departmentResponseToEntity.map { it.departmentId })) {
                        Napier.i(tag = this@DepartmentsDownloadMachine::class.simpleName) {
                            "All Departments Already Downloaded. Bailing Early"
                        }
                        return@launch
                    }

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

                    // Save the last download time to datastore
                    val lastDownloadTime = Clock.System.now().toEpochMilliseconds()
                    dataStoreRepository.saveLongValue(LAST_DOWNLOAD_TIME_DATASTORE_KEY, lastDownloadTime).also {
                        Napier.i(tag = this@DepartmentsDownloadMachine::class.simpleName) {
                            "Saved last download time to datastore: $lastDownloadTime"
                        }
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
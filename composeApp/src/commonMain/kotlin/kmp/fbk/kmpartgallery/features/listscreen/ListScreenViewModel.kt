package kmp.fbk.kmpartgallery.features.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.local_storage.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.local_storage.domain_models.Department
import kmp.fbk.kmpartgallery.local_storage.mappers.toArtPieceList
import kmp.fbk.kmpartgallery.networking.download.ArtPieceDownloadMachine
import kmp.fbk.kmpartgallery.networking.download.DepartmentsDownloadMachine
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtPieceResponse
import kmp.fbk.kmpartgallery.networking.response_data_models.DepartmentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListScreenViewModel(
    private val listScreenRepository: ListScreenRepository,
    private val departmentsDownloadMachine: DepartmentsDownloadMachine,
    private val artPieceDownloadMachine: ArtPieceDownloadMachine,
): ViewModel() {

    private val _state = MutableStateFlow<ViewModelState>(ViewModelState.Loading)
    val state = _state.asStateFlow()

    private val _departmentsList = MutableStateFlow<List<DepartmentResponse>>(emptyList())
    val departmentsList = _departmentsList.asStateFlow()

    private val _artPieceResponseList = MutableStateFlow<List<ArtPiece>>(emptyList())
    val artPieceResponseList = _artPieceResponseList.asStateFlow()

    init {
        viewModelScope.launch {


//            artPieceDownloadMachine.downloadArtPieces()

            launch {
                listScreenRepository.getAllArtPiecesFromDbFlow().collectLatest {
                    _artPieceResponseList.emit(it.toArtPieceList())
                }
            }

            launch {
                artPieceResponseList.collectLatest {
                    if (it.isNotEmpty()) {
                        _state.emit(ViewModelState.Success(Unit))
                    }
                }
            }
        }
    }

    fun insertDepartmentIntoDb(department: Department) {
        viewModelScope.launch {
            listScreenRepository.insertDepartmentIntoDb(department)
        }
    }

    private suspend fun getAllDepartments() {
        _departmentsList.emit(listScreenRepository.getAllDepartments())
    }

}
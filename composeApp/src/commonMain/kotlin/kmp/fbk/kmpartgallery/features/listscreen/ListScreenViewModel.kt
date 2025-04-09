package kmp.fbk.kmpartgallery.features.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.local_storage.dao.DepartmentDao
import kmp.fbk.kmpartgallery.local_storage.domain_models.Department
import kmp.fbk.kmpartgallery.local_storage.mappers.toDepartmentsList
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtPieceResponse
import kmp.fbk.kmpartgallery.networking.response_data_models.DepartmentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init
import org.koin.mp.KoinPlatform

class ListScreenViewModel(
    private val listScreenRepository: ListScreenRepository,
): ViewModel() {

    private val _state = MutableStateFlow<ViewModelState>(ViewModelState.Loading)
    val state = _state.asStateFlow()

    private val _departmentsList = MutableStateFlow<List<DepartmentResponse>>(emptyList())
    val departmentsList = _departmentsList.asStateFlow()

    private val _artPieceResponseList = MutableStateFlow<List<ArtPieceResponse>>(emptyList())
    val artPieceResponseList = _artPieceResponseList.asStateFlow()

    init {
        viewModelScope.launch {
//            getAllDepartments()
//            _state.emit(ViewModelState.Success(Unit))
            getFiveArtPieces()
        }
    }

    fun insertDepartmentIntoDb(department: Department) {
        viewModelScope.launch {
            listScreenRepository.insertDepartmentIntoDb(department)
        }
    }

    private suspend fun getFiveArtPieces() {
        val artPieceResponseList = mutableListOf<ArtPieceResponse>()
        (475..500).forEach {
            val artPiece = listScreenRepository.getArtPieceById(it)
            artPieceResponseList.add(artPiece)
            Napier.i(tag = "ListScreenViewModel", message = "artPiece: $artPiece")
        }
        _artPieceResponseList.emit(artPieceResponseList)
        _state.emit(ViewModelState.Success(Unit))
    }

    private suspend fun getAllDepartments() {
        _departmentsList.emit(listScreenRepository.getAllDepartments())
    }

}
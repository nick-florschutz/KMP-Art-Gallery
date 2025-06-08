package kmp.fbk.kmpartgallery.features.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kmp.fbk.kmpartgallery.ViewModelState
import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.domain_models.Department
import kmp.fbk.kmpartgallery.local_storage.database.mappers.toArtPieceList
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.search.ISearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ListScreenViewModel(
    private val listScreenRepository: ListScreenRepository,
): ViewModel(), ISearchViewModel {

    private val _state = MutableStateFlow<ViewModelState>(ViewModelState.Loading)
    val state = _state.asStateFlow()

    private val _featuredImagesListState = MutableStateFlow<FeaturedImagesListState>(FeaturedImagesListState.Loading)
    val featuredImagesListState = _featuredImagesListState.asStateFlow()

    private val _departmentsList = MutableStateFlow<List<Department>>(emptyList())
    val departmentsList = _departmentsList.asStateFlow()

    private val _artPieceResponseList = MutableStateFlow<List<ArtPiece>>(emptyList())
    val artPieceResponseList = _artPieceResponseList.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedDepartment = MutableStateFlow<String?>(null)
    val selectedDepartment = _selectedDepartment.asStateFlow()

    init {
        collectOnArtPieces()
        getFeaturedImages()
        collectOnLoadingState()
        collectOnDepartments()
    }

    override fun onSearchTextChange(query: String, additionalArgs: String?) {
        _searchQuery.tryEmit(query)
    }

    fun onAllDepartmentsSelected() {
        viewModelScope.launch {
            _state.emit(ViewModelState.Loading)
            _selectedDepartment.emit(null)
            collectOnArtPieces().also {
                _state.emit(ViewModelState.Success(Unit))
            }
        }
    }

    fun getArtPiecesByDepartment(department: String) {
        viewModelScope.launch {
            _selectedDepartment.emit(department)
            listScreenRepository.getAllArtPiecesByDepartmentFromDbFlow(department).collectLatest {
                _artPieceResponseList.emit(it).also {
                    _state.emit(ViewModelState.Success(Unit))
                }
            }
        }
    }

    private fun collectOnArtPieces() {
        viewModelScope.launch {
            listScreenRepository.getAllArtPiecesFromDbFlow().collectLatest {
                _artPieceResponseList.emit(it)
            }
        }
    }

    private fun collectOnLoadingState() {
        viewModelScope.launch(Dispatchers.Default) {
            artPieceResponseList.collectLatest {
                if (it.isNotEmpty()) {
                    _state.emit(ViewModelState.Success(Unit))
                }
            }
        }
    }

    private fun getFeaturedImages() {
        viewModelScope.launch(Dispatchers.Default) {
//            listScreenRepository.getFiveArtPiecePrimaryImagesFlow().collectLatest { featuredImages ->
//                if (featuredImages.isNotEmpty()) {
//                    _featuredImagesListState.emit(FeaturedImagesListState.Success(featuredImages))
//                } else {
//                    delay(3500)
//                    _featuredImagesListState.emit(FeaturedImagesListState.Error)
//                }
//            }

//            val featuredImages = listScreenRepository.getFiveArtPiecePrimaryImagesFlow().first().map { IdsAndImagesFlow ->
//                IdsAndImagesFlow.entries.map { mapOfIdAndImage ->
//                    ArtPieceLocalIdAndImage(
//                        localId = mapOfIdAndImage.key,
//                        image = mapOfIdAndImage.value,
//                    )
//                }
//            }

            val featuredImages = listScreenRepository.getFiveArtPiecesFlow().first().map {
                // The DAO query specifies that these two values will NOT be null
                ArtPieceLocalIdAndImage(
                    localId = it.localId!!,
                    image = it.primaryImage!!,
                )
            }

            if (featuredImages.isNotEmpty()) {
                _featuredImagesListState.emit(FeaturedImagesListState.Success(featuredImages))
            } else {
                _featuredImagesListState.emit(FeaturedImagesListState.Error)
            }

        }
    }

    private fun collectOnDepartments() {
        viewModelScope.launch(Dispatchers.Default) {
            listScreenRepository.getAllDepartmentsFromDbFlow().collectLatest {
                _departmentsList.emit(it)
            }
        }
    }

}
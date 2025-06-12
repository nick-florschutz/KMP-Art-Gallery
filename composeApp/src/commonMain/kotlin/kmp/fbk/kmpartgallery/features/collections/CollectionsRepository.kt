package kmp.fbk.kmpartgallery.features.collections

import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.local_storage.database.dao.ArtPieceDao
import kmp.fbk.kmpartgallery.local_storage.database.mappers.toArtPieceList
import kotlinx.coroutines.flow.first

class CollectionsRepository(
    private val artPieceDao: ArtPieceDao,
) {
    /**
     * Retrieves all art pieces from the local database and groups them by department.
     * @return A [Map] where keys are department names (non-null and non-empty)
     * and values are lists of [ArtPiece] objects belonging to that department.
     */
    suspend fun getAllArtPiecesWithDepartmentsMap(): Map<String, List<ArtPiece>> {
        val artPieces = artPieceDao.getAllArtPieces().toArtPieceList()
        val departments = artPieces.map { it.department }.distinct()

        val artPiecesAndDepartmentsMap = mutableMapOf<String, List<ArtPiece>>()
        departments.forEach { department ->
            if (department.isNullOrBlank()) return@forEach // Skip null & empty departments

            val departmentArtPieces = artPieces
                .toSet()
                .filter { it.department == department }
                .sortedBy { it.primaryImage.isNullOrBlank() }
                .take(10) // Take the first 10 art pieces for each department

            artPiecesAndDepartmentsMap[department] = departmentArtPieces
        }

        return artPiecesAndDepartmentsMap
    }

    suspend fun getAllArtPiecesByDepartment(department: String): List<ArtPiece> {
        return artPieceDao.getAllArtPiecesByDepartmentFlow(department)
            .first()
            .toArtPieceList()
    }
}
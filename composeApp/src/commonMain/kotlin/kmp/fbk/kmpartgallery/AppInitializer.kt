package kmp.fbk.kmpartgallery

import kmp.fbk.kmpartgallery.networking.download.ArtPieceDownloadMachine
import kmp.fbk.kmpartgallery.networking.download.DepartmentsDownloadMachine

class AppInitializer(
    private val artPieceDownloadMachine: ArtPieceDownloadMachine,
    private val departmentsDownloadMachine: DepartmentsDownloadMachine,
) {
    fun runInitJobs() {
        artPieceDownloadMachine.downloadArtPieces()
        departmentsDownloadMachine.downloadDepartments()
    }
}
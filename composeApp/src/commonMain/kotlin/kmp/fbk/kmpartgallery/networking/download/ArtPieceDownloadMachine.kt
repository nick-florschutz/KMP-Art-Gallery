package kmp.fbk.kmpartgallery.networking.download

import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.local_storage.dao.ArtPieceDao
import kmp.fbk.kmpartgallery.local_storage.mappers.toArtPiece
import kmp.fbk.kmpartgallery.local_storage.mappers.toArtPieceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

class ArtPieceDownloadMachine(
    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val artPieceDao: ArtPieceDao,
): DownloadMachine() {

    companion object {
        private const val NUM_REQUESTS_PER_SECOND_LIMIT = 50
        private const val NUM_ITEMS_TO_FETCH = 500
    }

    @OptIn(ExperimentalTime::class)
    fun downloadArtPieces() {
        coroutineScope.launch {
            var currentIndex = 0
            val now = Clock.System.now()
            val startTime = now.toEpochMilliseconds()

            val storedArtPieceObjectIds = artPieceDao.getAllArtPieceObjectIds()

            Napier.i(tag = this@ArtPieceDownloadMachine::class.simpleName) {
                "Downloading Art Pieces Starting"
            }

            while (currentIndex < NUM_ITEMS_TO_FETCH) {
                    // Chunk the requests into groups of 50 per second
                    (currentIndex..(currentIndex + NUM_REQUESTS_PER_SECOND_LIMIT)).forEach { index ->
                        val artPiece = metArtApi.getArtPieceById(index)
                            .toArtPiece()

                        // Prevent inserting bad data into the database
                        if (artPiece.objectID != null && !storedArtPieceObjectIds.contains(artPiece.objectID)) {
                            artPieceDao.insert(artPiece.toArtPieceEntity())
                        }
                    }
                // Force delay to limit requests per second
                delay(1000)
                currentIndex += NUM_REQUESTS_PER_SECOND_LIMIT
            }

            Napier.i(tag = this@ArtPieceDownloadMachine::class.simpleName) {
                "Downloading Art Pieces Ending: ${(Clock.System.now().toEpochMilliseconds() - startTime).milliseconds} Elapsed"
            }
        }
    }
}
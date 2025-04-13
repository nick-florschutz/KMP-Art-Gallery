package kmp.fbk.kmpartgallery.networking.download

import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.local_storage.dao.ArtPieceDao
import kmp.fbk.kmpartgallery.local_storage.mappers.toArtPiece
import kmp.fbk.kmpartgallery.local_storage.mappers.toArtPieceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
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
            val now = Clock.System.now()
            val startTime = now.toEpochMilliseconds()

            val storedArtPieceObjectIds = artPieceDao.getAllArtPieceObjectIds()

            val totalObjectsAndIds = getTotalObjectsAndIds()
            val totalObjects = totalObjectsAndIds.first

            val randomSubsetNumber = Random.nextInt(0, totalObjects ?: return@launch)
            val objectIds = if (randomSubsetNumber + NUM_ITEMS_TO_FETCH < totalObjects) {
                totalObjectsAndIds.second.subList(
                    fromIndex = randomSubsetNumber,
                    toIndex = randomSubsetNumber + NUM_ITEMS_TO_FETCH,
                )
            } else {
                totalObjectsAndIds.second.subList(
                    fromIndex = randomSubsetNumber,
                    toIndex = randomSubsetNumber - NUM_ITEMS_TO_FETCH,
                )
            }.filterNot { objectId ->
                storedArtPieceObjectIds.contains(objectId)
            }

            var currentIndex = 0

            Napier.i(tag = this@ArtPieceDownloadMachine::class.simpleName) {
                "Downloading Art Pieces Starting. Items found: $totalObjects. Items to download: ${objectIds.size}"
            }

            if (totalObjects == storedArtPieceObjectIds.size || storedArtPieceObjectIds.containsAll(objectIds)) {
                Napier.i(tag = this@ArtPieceDownloadMachine::class.simpleName) {
                    "All Art Pieces Already Downloaded. Bailing Early"
                }
                return@launch
            }

            // Chunk the requests into groups of 50 per second
            objectIds.chunked(50).forEach { chunk ->
                chunk.forEach { objectId ->
                    if (objectId != null) {
                        val artPiece = metArtApi.getArtPieceById(objectId)
                            .toArtPiece()
                        // Prevent inserting bad data into the database
                        if (artPiece.objectID != null && !storedArtPieceObjectIds.contains(artPiece.objectID)) {
                            artPieceDao.insert(artPiece.toArtPieceEntity())
                        }
                    }
                }
                Napier.i(tag = this@ArtPieceDownloadMachine::class.simpleName) {
                    "Progress Update: $currentIndex / ${objectIds.size} downloaded. Time Elapsed: ${(Clock.System.now().toEpochMilliseconds() - startTime).milliseconds}"
                }

                // Force delay to limit requests per second
                delay(1000)
                currentIndex += NUM_REQUESTS_PER_SECOND_LIMIT
            }

            Napier.i(tag = this@ArtPieceDownloadMachine::class.simpleName) {
                "Downloaded ${objectIds.size} Art Pieces Successfully. Time Elapsed: ${(Clock.System.now().toEpochMilliseconds() - startTime).milliseconds}"
            }
        }
    }

    /**
     * Gets the total number of objects and their ids
     */
    private suspend fun getTotalObjectsAndIds(): Pair<Int?, List<Int?>> {
        val totalObjects = metArtApi.getAllArtPiecesIds()
        return totalObjects.total to totalObjects.objectIDs
    }
}
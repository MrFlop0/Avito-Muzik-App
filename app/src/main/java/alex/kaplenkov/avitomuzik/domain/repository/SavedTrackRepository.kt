package alex.kaplenkov.avitomuzik.domain.repository

import alex.kaplenkov.avitomuzik.data.db.entity.TrackEntity
import alex.kaplenkov.avitomuzik.domain.model.SearchModel
import alex.kaplenkov.avitomuzik.domain.model.Track
import android.content.Context

interface SavedTrackRepository {

    suspend fun getSavedTracks(): List<Track>

    suspend fun searchSavedTracks(query: String): SearchModel

    suspend fun addTrack(track: TrackEntity)

    suspend fun getTrackById(id: Long): Track?

    suspend fun deleteTrack(id: Long)

    suspend fun saveTrack(track: Track, context: Context): TrackEntity?
}
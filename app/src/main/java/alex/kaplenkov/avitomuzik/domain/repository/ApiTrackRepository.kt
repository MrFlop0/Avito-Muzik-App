package alex.kaplenkov.avitomuzik.domain.repository

import alex.kaplenkov.avitomuzik.domain.model.Chart
import alex.kaplenkov.avitomuzik.domain.model.SearchModel
import alex.kaplenkov.avitomuzik.domain.model.Track

interface ApiTrackRepository {

    suspend fun getTopChart(): Chart?

    suspend fun getTrackById(id: Long): Track?

    suspend fun searchTracks(query: String): SearchModel?

}
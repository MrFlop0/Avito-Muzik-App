package alex.kaplenkov.avitomuzik.data.repository

import alex.kaplenkov.avitomuzik.data.api.DeezerApiService
import alex.kaplenkov.avitomuzik.data.api.dto.toChart
import alex.kaplenkov.avitomuzik.data.api.dto.toSearchModel
import alex.kaplenkov.avitomuzik.data.api.dto.toTrack
import alex.kaplenkov.avitomuzik.domain.model.Chart
import alex.kaplenkov.avitomuzik.domain.model.SearchModel
import alex.kaplenkov.avitomuzik.domain.model.Track
import alex.kaplenkov.avitomuzik.domain.repository.ApiTrackRepository
import android.util.Log
import okio.IOException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiTrackRepositoryImpl @Inject constructor(
    private val apiService: DeezerApiService
): ApiTrackRepository {

    override suspend fun getTopChart(): Chart? {
        try {
            val response = apiService.getTopCharts()
            return response.body()?.toChart()
        } catch (e: IOException) {
            Log.d("ApiRepo", "couldn't fetch chart")
            return null
        }
    }

    override suspend fun getTrackById(id: Long): Track? {
        try {
            val response = apiService.getTrack(id)
            return response.body()?.toTrack()
        } catch (e: IOException) {
            Log.d("ApiRepo", "couldn't fetch track by id")
            return null
        }
    }

    override suspend fun searchTracks(query: String): SearchModel? {
        try {
            val response = apiService.searchTrack(query)
            return response.body()?.toSearchModel()
        } catch (e: IOException) {
            Log.d("ApiRepo", "couldn't fetch tracks by search")
            return null
        }
    }

}
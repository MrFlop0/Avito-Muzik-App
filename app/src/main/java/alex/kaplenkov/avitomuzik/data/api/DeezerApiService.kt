package alex.kaplenkov.avitomuzik.data.api

import alex.kaplenkov.avitomuzik.data.api.dto.ChartDto
import alex.kaplenkov.avitomuzik.data.api.dto.SearchDto
import alex.kaplenkov.avitomuzik.data.api.dto.TrackDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {
    @GET("/chart")
    suspend fun getTopCharts(): Response<ChartDto>

    @GET("/search")
    suspend fun searchTrack(@Query("q") query: String): Response<SearchDto>

    @GET("/track/{id}")
    suspend fun getTrack(@Path("id") id: Long): Response<TrackDto>
}
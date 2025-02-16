package alex.kaplenkov.avitomuzik.data.api.dto

import alex.kaplenkov.avitomuzik.domain.model.Chart
import com.google.gson.annotations.SerializedName

data class ChartDto(
    @SerializedName("tracks")
    val tracks: ChartData
)

data class ChartData(
    @SerializedName("data")
    val data: List<TrackDto>,
    @SerializedName("total")
    val total: Int
)

fun ChartDto.toChart() =
    Chart(
        tracks = tracks.data.map { it.toTrack() }
    )

package alex.kaplenkov.avitomuzik.data.api.dto

import alex.kaplenkov.avitomuzik.domain.model.SearchModel
import com.google.gson.annotations.SerializedName

data class SearchDto(
    @SerializedName("data")
    val tracks: List<TrackDto>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("next")
    val nextPage: String
)

fun SearchDto.toSearchModel() =
    SearchModel(
        tracks = tracks.map { it.toTrack() },
        total = total
    )
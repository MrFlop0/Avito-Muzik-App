package alex.kaplenkov.avitomuzik.data.api.dto


import alex.kaplenkov.avitomuzik.domain.model.Album
import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("cover")
    val cover: String,
    @SerializedName("cover_big")
    val coverBig: String,
    @SerializedName("cover_medium")
    val coverMedium: String,
    @SerializedName("cover_small")
    val coverSmall: String,
    @SerializedName("cover_xl")
    val coverXl: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("link")
    val link: String?,
    @SerializedName("md5_image")
    val md5Image: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("tracklist")
    val trackList: String,
    @SerializedName("type")
    val type: String
)

fun AlbumDto.toAlbum() =
    Album(
        id = id,
        link = link,
        title = title,
        releaseDate = releaseDate,
        cover = cover,
        coverBig = coverBig,
        coverMedium = coverMedium,
        coverSmall = coverSmall,
        coverXl = coverXl,
        trackList = trackList,
    )
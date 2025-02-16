package alex.kaplenkov.avitomuzik.data.api.dto


import alex.kaplenkov.avitomuzik.domain.model.Artist
import alex.kaplenkov.avitomuzik.domain.model.Contributor
import alex.kaplenkov.avitomuzik.domain.model.Track
import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("album")
    val album: AlbumDto,
    @SerializedName("artist")
    val artist: ArtistDto,
    @SerializedName("available_countries")
    val availableCountries: List<Any?>,
    @SerializedName("bpm")
    val bpm: Double,
    @SerializedName("contributors")
    val contributors: List<ContributorDto>,
    @SerializedName("disk_number")
    val diskNumber: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Int,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Int,
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,
    @SerializedName("gain")
    val gain: Double,
    @SerializedName("id")
    val id: Long,
    @SerializedName("isrc")
    val isrc: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("md5_image")
    val md5Image: String,
    @SerializedName("preview")
    val preview: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("readable")
    val readable: Boolean,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("share")
    val share: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_short")
    val titleShort: String,
    @SerializedName("title_version")
    val titleVersion: String,
    @SerializedName("track_position")
    val trackPosition: Int,
    @SerializedName("track_token")
    val trackToken: String,
    @SerializedName("type")
    val type: String
)

fun TrackDto.toTrack() =
    Track(
        album = album.toAlbum(),
        artist = artist.toArtist(),
        contributors = contributors?.map { it.toContributor() },
        duration = duration,
        explicitContentLyrics = explicitContentLyrics,
        explicitLyrics = explicitLyrics,
        id = id,
        link = link,
        preview = preview,
        title = title,
        titleShort = titleShort,
    )

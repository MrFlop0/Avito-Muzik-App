package alex.kaplenkov.avitomuzik.data.db.entity

import alex.kaplenkov.avitomuzik.domain.model.Album
import alex.kaplenkov.avitomuzik.domain.model.Artist
import alex.kaplenkov.avitomuzik.domain.model.Track
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val artistName: String,
    val artistId: Long,
    val cover: String,
    val coverXl: String?,
    val albumTitle: String?,
    val duration: Int,
    val explicitContentLyrics: Int,
    val explicitLyrics: Boolean,
    val link: String,
    val preview: String,
    val title: String,
    val titleShort: String,
)

fun TrackEntity.toTrack(): Track =
    Track(
        album = Album(cover = cover, coverXl = coverXl, title = albumTitle),
        artist = Artist(name = artistName, id = artistId),
        contributors = null,
        duration = duration,
        explicitContentLyrics = explicitContentLyrics,
        explicitLyrics = explicitLyrics,
        id = id,
        link = link,
        preview = preview,
        title = title,
        titleShort = titleShort,
    )
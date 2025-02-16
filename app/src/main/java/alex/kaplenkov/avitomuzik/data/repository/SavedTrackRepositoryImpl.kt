package alex.kaplenkov.avitomuzik.data.repository

import alex.kaplenkov.avitomuzik.data.db.TrackDao
import alex.kaplenkov.avitomuzik.data.db.entity.TrackEntity
import alex.kaplenkov.avitomuzik.data.db.entity.toTrack
import alex.kaplenkov.avitomuzik.domain.model.SearchModel
import alex.kaplenkov.avitomuzik.domain.model.Track
import alex.kaplenkov.avitomuzik.domain.repository.SavedTrackRepository
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Singleton

@Singleton
class SavedTrackRepositoryImpl(
    private val trackDao: TrackDao
) : SavedTrackRepository {

    override suspend fun getSavedTracks(): List<Track> {
        return trackDao.getAllTracks().map { it.toTrack() }
    }

    override suspend fun searchSavedTracks(query: String): SearchModel {
        val result = trackDao.searchTrack(query).map { it.toTrack() }
        return SearchModel(
            tracks = result,
            total = result.size
        )
    }

    override suspend fun addTrack(track: TrackEntity) {
        trackDao.insertTrack(track)
    }

    override suspend fun getTrackById(id: Long): Track? {
        return trackDao.getTrackById(id)?.toTrack()
    }

    override suspend fun deleteTrack(id: Long) {
        trackDao.getTrackById(id)?.toTrack()?.let {
            val audioFile = File(it.preview)
            val coverFile = File(it.album.cover)

            audioFile.delete()
            coverFile.delete()
            trackDao.deleteTrackById(id)
        }

    }

    override suspend fun saveTrack(track: Track, context: Context): TrackEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val trackFileName = "track_${track.id}.mp3"
                val coverFileName = "cover_${track.id}.jpg"

                val trackUri = downloadFile(track.preview, context, trackFileName)
                val coverUri = track.album.coverXl?.let {
                    downloadFile(it, context, coverFileName)
                } ?: downloadFile(track.album.cover, context, coverFileName)

                if (trackUri == null || coverUri == null) {
                    null
                } else {
                    track.toTrackEntity().copy(
                        preview = trackUri.toString(),
                        coverXl = coverUri.toString(),
                        cover = coverUri.toString()
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun downloadFile(url: String, context: Context, fileName: String): Uri? {
        try {
            val input = URL(url).openStream()
            val directory = context.getExternalFilesDir(null) ?: context.filesDir
            val file = File(directory, fileName)
            val output = FileOutputStream(file)
            input.copyTo(output)
            output.close()
            input.close()
            return file.toUri()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun Track.toTrackEntity(): TrackEntity =
        TrackEntity(
            duration = duration,
            explicitContentLyrics = explicitContentLyrics,
            explicitLyrics = explicitLyrics,
            id = id,
            link = link,
            preview = preview,
            title = title,
            titleShort = titleShort,
            artistName = artist.name,
            artistId = artist.id,
            cover = album.cover,
            coverXl = album.coverXl,
            albumTitle = album.title
        )
}
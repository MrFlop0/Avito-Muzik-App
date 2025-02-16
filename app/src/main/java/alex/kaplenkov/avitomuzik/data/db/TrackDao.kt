package alex.kaplenkov.avitomuzik.data.db

import alex.kaplenkov.avitomuzik.data.db.entity.TrackEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query("DELETE FROM trackentity where id = :id")
    suspend fun deleteTrackById(id: Long)

    @Query("SELECT * FROM trackentity")
    suspend fun getAllTracks(): List<TrackEntity>

    @Query("SELECT * FROM trackentity WHERE title LIKE '%' || :query || '%' OR artistName LIKE '%' || :query || '%'")
    suspend fun searchTrack(query: String): List<TrackEntity>

    @Query("SELECT * FROM trackentity where id = :id")
    suspend fun getTrackById(id: Long): TrackEntity?
}
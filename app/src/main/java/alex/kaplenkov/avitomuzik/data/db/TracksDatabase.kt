package alex.kaplenkov.avitomuzik.data.db

import alex.kaplenkov.avitomuzik.data.db.entity.TrackEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TrackEntity::class],
    version = 2
)
abstract class TracksDatabase : RoomDatabase() {
    abstract val dao: TrackDao
}
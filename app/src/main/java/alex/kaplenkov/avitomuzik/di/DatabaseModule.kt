package alex.kaplenkov.avitomuzik.di

import alex.kaplenkov.avitomuzik.data.db.TrackDao
import alex.kaplenkov.avitomuzik.data.db.TracksDatabase
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTrackDatabase(@ApplicationContext context: Context): TracksDatabase {
        return Room.databaseBuilder(
            context,
            TracksDatabase::class.java,
            "trackentity"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTrackDao(database: TracksDatabase): TrackDao {
        return database.dao
    }

}
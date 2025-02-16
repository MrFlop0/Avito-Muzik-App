package alex.kaplenkov.avitomuzik.di

import alex.kaplenkov.avitomuzik.data.api.DeezerApiService
import alex.kaplenkov.avitomuzik.data.db.TrackDao
import alex.kaplenkov.avitomuzik.data.repository.ApiTrackRepositoryImpl
import alex.kaplenkov.avitomuzik.data.repository.SavedTrackRepositoryImpl
import alex.kaplenkov.avitomuzik.domain.repository.ApiTrackRepository
import alex.kaplenkov.avitomuzik.domain.repository.SavedTrackRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiTrackRepository(apiService: DeezerApiService): ApiTrackRepository {
        return ApiTrackRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSavedTrackRepository(dao: TrackDao): SavedTrackRepository {
        return SavedTrackRepositoryImpl(dao)
    }
}
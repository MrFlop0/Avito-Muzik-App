package alex.kaplenkov.avitomuzik.presentation.viewmodel

import alex.kaplenkov.avitomuzik.domain.model.Track
import alex.kaplenkov.avitomuzik.domain.repository.ApiTrackRepository
import alex.kaplenkov.avitomuzik.domain.repository.SavedTrackRepository
import alex.kaplenkov.avitomuzik.service.MusicService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val apiTrackRepository: ApiTrackRepository,
    private val savedTrackRepository: SavedTrackRepository,
) : ViewModel() {

    private var musicService: MusicService? = null
    private var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isServiceBound = false
        }
    }

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved

    private val _trackProgress = MutableStateFlow(0)
    val trackProgress: StateFlow<Int> = _trackProgress

    private val _trackDuration = MutableStateFlow(0)
    val trackDuration: StateFlow<Int> = _trackDuration

    private var currentTrackIndex = 0
    private var trackList: List<Track> = emptyList()

    fun fetchNextTracks() {
        viewModelScope.launch {
            trackList = savedTrackRepository.getSavedTracks()
        }
    }

    fun fetchTrack(id: Long) {
        viewModelScope.launch {
            val track = apiTrackRepository.getTrackById(id) ?: savedTrackRepository.getTrackById(id)
            setTrack(track)
//            _currentTrack.value = track
//            _trackDuration.value = track?.duration ?: 30
//            _trackProgress.value = musicService?.getTrackProgress() ?: 0
            play()
        }
    }

    fun play() {
        _currentTrack.value?.let {
            musicService?.playTrack(it.preview, it.titleShort)
            _isPlaying.value = true
            startProgressTracking()
        }
    }

    fun pause() {
        musicService?.pauseTrack()
        _isPlaying.value = false
    }

    fun seekTo(position: Int) {
        if (position in 0..(_trackDuration.value)) {
            _trackProgress.value = position
            musicService?.seekTo(position * 1000)
        }
    }

    private fun startProgressTracking() {
        viewModelScope.launch {
            while (_isPlaying.value) {
                _trackProgress.value = musicService?.getTrackProgress()?.div(1000) ?: 0
                _trackDuration.value = musicService?.getTrackDuration()?.div(1000) ?: 0
                delay(1000L)
            }
        }
    }

    fun nextTrack() {
        if (trackList.isNotEmpty()) {
            currentTrackIndex = (currentTrackIndex + 1) % trackList.size
            val track = trackList[currentTrackIndex]
            setTrack(track)
            musicService?.playTrack(track.preview, track.titleShort)
        }
    }

    fun previousTrack() {
        if (trackList.isNotEmpty()) {
            currentTrackIndex = (currentTrackIndex - 1 + trackList.size) % trackList.size
            val track = trackList[currentTrackIndex]
            setTrack(track)
            musicService?.playTrack(track.preview, track.titleShort)
        }
    }

    fun bindService(context: Context) {
        Intent(context, MusicService::class.java).also { intent ->
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    fun unbindService(context: Context) {
        if (isServiceBound) {
            context.unbindService(serviceConnection)
            isServiceBound = false
        }
    }

    fun toggleTrackSaved(track: Track, context: Context) {
        viewModelScope.launch {
            if (!isSaved.value) {
                addToSaved(track, context)
            } else {
                savedTrackRepository.deleteTrack(track.id)
                _isSaved.value = false
            }
        }
    }

    private suspend fun addToSaved(track: Track, context: Context) {
        val savedTrack = savedTrackRepository.saveTrack(track, context)
        savedTrack?.let {
            savedTrackRepository.addTrack(it)
            _isSaved.value = true
        }
    }

    private fun setTrack(track: Track?) {
        viewModelScope.launch {
            _currentTrack.value = track
            _trackDuration.value = track?.duration ?: 0
            _trackProgress.value = 0
            _trackProgress.value = musicService?.getTrackProgress() ?: 0
            _isSaved.value = savedTrackRepository.getTrackById(track?.id ?: -1) != null
        }
    }

}
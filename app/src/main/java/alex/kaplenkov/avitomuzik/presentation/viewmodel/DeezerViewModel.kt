package alex.kaplenkov.avitomuzik.presentation.viewmodel

import alex.kaplenkov.avitomuzik.domain.model.Chart
import alex.kaplenkov.avitomuzik.domain.model.SearchModel
import alex.kaplenkov.avitomuzik.domain.model.Track
import alex.kaplenkov.avitomuzik.domain.repository.ApiTrackRepository
import alex.kaplenkov.avitomuzik.domain.repository.SavedTrackRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeezerViewModel @Inject constructor(
    private val apiTrackRepository: ApiTrackRepository,
    private val savedTrackRepository: SavedTrackRepository
): ViewModel() {

    private val _topChart = MutableStateFlow<Chart?>(null)
    val topChart: StateFlow<Chart?> = _topChart

    private val _apiSearchResult = MutableStateFlow<SearchModel?>(null)
    val apiSearchResult: StateFlow<SearchModel?> = _apiSearchResult

    private val _savedSearchResult = MutableStateFlow<SearchModel?>(null)
    val savedSearchResult: StateFlow<SearchModel?> = _savedSearchResult

    private val _savedTracks = MutableStateFlow<List<Track>?>(emptyList())
    val savedTracks: StateFlow<List<Track>?> = _savedTracks


    fun fetchTopChart() {
        viewModelScope.launch {
            _topChart.value = apiTrackRepository.getTopChart()
        }
    }

    fun fetchSavedTracks() {
        viewModelScope.launch {
            _savedTracks.value = savedTrackRepository.getSavedTracks()
        }
    }

    fun searchApiTracks(query: String) {
        if (query.isEmpty()) {
            _apiSearchResult.value = null
            return
        }

        viewModelScope.launch {
            _apiSearchResult.value = apiTrackRepository.searchTracks(query)
        }
    }

    fun searchSavedTracks(query: String) {
        if (query.isEmpty()) {
            _savedSearchResult.value = null
            return
        }

        viewModelScope.launch {
            _savedSearchResult.value = savedTrackRepository.searchSavedTracks(query)
        }
    }
}
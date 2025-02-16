package alex.kaplenkov.avitomuzik.presentation.ui.screens

import alex.kaplenkov.avitomuzik.presentation.ui.common.BaseTracksScreen
import alex.kaplenkov.avitomuzik.presentation.viewmodel.DeezerViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController


@Composable
fun SavedTracksScreen(navController: NavHostController, viewModel: DeezerViewModel) {
    val list by viewModel.savedTracks.collectAsState()
    val searchModel by viewModel.savedSearchResult.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSavedTracks()
    }

    val tracks = remember(list, searchModel) {
        searchModel?.tracks ?: list.orEmpty()
    }
    BaseTracksScreen(
        navController = navController,
        tracks = tracks,
        onSearch = { viewModel.searchSavedTracks(it) },
        label = "Search Saved Tracks"
    )
}
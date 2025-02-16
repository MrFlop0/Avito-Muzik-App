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
fun ApiTracksScreen(navController: NavHostController, viewModel: DeezerViewModel) {
    val chart by viewModel.topChart.collectAsState()
    val searchModel by viewModel.apiSearchResult.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTopChart()
    }

    val tracks = remember(chart, searchModel) {
        searchModel?.tracks ?: chart?.tracks.orEmpty()
    }
    BaseTracksScreen(
        navController = navController,
        tracks = tracks,
        onSearch = { viewModel.searchApiTracks(it) },
        label = "Search API Tracks"
    )
}

package alex.kaplenkov.avitomuzik.presentation.ui.common

import alex.kaplenkov.avitomuzik.domain.model.Track
import alex.kaplenkov.avitomuzik.presentation.ui.TrackItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun BaseTracksScreen(
    navController: NavHostController,
    tracks: List<Track>,
    onSearch: (String) -> Unit,
    label: String,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val debounceSearch = remember { MutableStateFlow("") }

    LaunchedEffect(searchQuery) {
        debounceSearch.value = searchQuery
        debounceSearch
            .debounce(500)
            .collectLatest { onSearch(it) }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
        )
        if (tracks.isNotEmpty()) {
            LazyColumn {
                items(tracks) { track ->
                    TrackItem(track) { navController.navigate("player/${track.id}") }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("There is no available tracks to display")
            }
        }
    }
}
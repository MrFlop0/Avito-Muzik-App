package alex.kaplenkov.avitomuzik.presentation.ui.screens

import alex.kaplenkov.avitomuzik.R
import alex.kaplenkov.avitomuzik.domain.model.Track
import alex.kaplenkov.avitomuzik.presentation.ui.common.Cover
import alex.kaplenkov.avitomuzik.presentation.viewmodel.PlayerViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PlayerScreen(navController: NavHostController, trackId: Long, viewModel: PlayerViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchNextTracks()
        viewModel.bindService(context)
        viewModel.fetchTrack(trackId)
    }

    val currentTrack by viewModel.currentTrack.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.trackProgress.collectAsState()
    val duration by viewModel.trackDuration.collectAsState()
    val isSaved by viewModel.isSaved.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        currentTrack?.let { track ->
            val cover = track.album.coverXl ?: track.album.cover
            Cover(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                cover = cover
            )
            Spacer(modifier = Modifier.height(16.dp))
            CommonInfo(
                track = track,
                isSaved = isSaved
            ) { viewModel.toggleTrackSaved(it, context) }
            Spacer(modifier = Modifier.height(16.dp))

            Slider(
                value = progress.toFloat(),
                onValueChange = { viewModel.seekTo(it.toInt()) },
                valueRange = 0f..duration.toFloat(),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formatTime(progress))
                Text(formatTime(duration))
            }

            Spacer(modifier = Modifier.height(16.dp))
            TrackControls(
                isPlaying = isPlaying,
                onPreviousClick = { viewModel.previousTrack() },
                onNextClick =  {viewModel.nextTrack() },
                onPlayPauseClick = { if (isPlaying) viewModel.pause() else viewModel.play() }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                Text("Back")
            }
        }
    }
}

@Composable
fun TrackControls(
    isPlaying: Boolean,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onPreviousClick) {
            Icon(
                painterResource(id = R.drawable.previous),
                contentDescription = "Previous Track"
            )
        }
        IconButton(onClick = onPlayPauseClick) {
            Icon(
                painterResource(if (isPlaying) R.drawable.pause else R.drawable.play),
                contentDescription = "Play/Pause"
            )
        }
        IconButton(onClick = onNextClick) {
            Icon(painterResource(id = R.drawable.next), contentDescription = "Next Track")
        }
    }
}

@Composable
private fun CommonInfo(
    track: Track,
    isSaved: Boolean,
    onSaveClick: (Track) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(track.title, style = MaterialTheme.typography.bodyMedium)
            Text(
                track.album.title ?: "Unknown Album",
                style = MaterialTheme.typography.labelSmall
            )
            Text(track.artist.name, style = MaterialTheme.typography.labelLarge)
        }
        IconButton(
            modifier = Modifier.minimumInteractiveComponentSize(),
            onClick = { onSaveClick(track) }
        ) {
            Icon(
                painter = if (!isSaved) {
                    painterResource(id = R.drawable.add)
                } else {
                    painterResource(id = R.drawable.delete)
                },
                contentDescription = "Add Track"
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "%02d:%02d".format(minutes, secs)
}
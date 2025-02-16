package alex.kaplenkov.avitomuzik.presentation.ui

import alex.kaplenkov.avitomuzik.domain.model.Track
import alex.kaplenkov.avitomuzik.presentation.ui.common.Cover
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TrackItem(track: Track, onTrackClick: (Track) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTrackClick(track) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Cover(
            modifier = Modifier.size(64.dp),
            cover = track.album.cover,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(track.title, style = MaterialTheme.typography.bodyLarge)
            Text(track.artist.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
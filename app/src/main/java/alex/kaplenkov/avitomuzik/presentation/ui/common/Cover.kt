package alex.kaplenkov.avitomuzik.presentation.ui.common

import alex.kaplenkov.avitomuzik.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun Cover(modifier: Modifier, cover: String, contentDesc: String? = null) {
    var isLoading by remember { mutableStateOf(true) }
    Box(
        modifier.then(
            Modifier.placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.shimmer(highlightColor = Color.Gray),
                color = Color.Transparent,
            )
        )
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = cover,
            contentDescription = contentDesc,
            onSuccess = { isLoading = false }
        )
    }
}
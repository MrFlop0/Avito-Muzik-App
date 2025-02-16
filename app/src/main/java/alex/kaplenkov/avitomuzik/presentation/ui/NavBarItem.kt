package alex.kaplenkov.avitomuzik.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    data object ApiScreenBadge : NavBarItem(
        title = "API Tracks",
        route = "api_tracks",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )

    data object SavedTracksBadge : NavBarItem(
        title = "Saved Tracks",
        route = "saved_tracks",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    companion object {
        fun getAllBadges(): List<NavBarItem> {
            return listOf(ApiScreenBadge, SavedTracksBadge)
        }
    }
}
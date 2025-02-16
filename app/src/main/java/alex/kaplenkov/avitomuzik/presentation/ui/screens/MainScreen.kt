package alex.kaplenkov.avitomuzik.presentation.ui.screens

import alex.kaplenkov.avitomuzik.presentation.ui.NavBarItem
import alex.kaplenkov.avitomuzik.presentation.viewmodel.DeezerViewModel
import alex.kaplenkov.avitomuzik.presentation.viewmodel.PlayerViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(viewModel: DeezerViewModel, playerViewModel: PlayerViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "api_tracks",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("api_tracks") { ApiTracksScreen(navController, viewModel) }
            composable("saved_tracks") { SavedTracksScreen(navController, viewModel) }
            composable("player/{trackId}") { backStackEntry ->
                val trackId = backStackEntry.arguments?.getString("trackId")?.toLongOrNull()
                trackId?.let {
                    PlayerScreen(navController, it, playerViewModel)
                }

            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation {
        val items = NavBarItem.getAllBadges()
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        items.forEachIndexed { index, badge ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector =
                        if (index == selectedItemIndex) {
                            badge.selectedIcon
                        } else {
                            badge.unselectedIcon
                        },
                        contentDescription = badge.title
                    )
                },
                label = { Text(badge.title) },
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(badge.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
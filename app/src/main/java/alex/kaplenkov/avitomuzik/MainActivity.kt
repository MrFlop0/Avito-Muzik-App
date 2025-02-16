package alex.kaplenkov.avitomuzik

import alex.kaplenkov.avitomuzik.presentation.ui.screens.MainScreen
import alex.kaplenkov.avitomuzik.presentation.viewmodel.DeezerViewModel
import alex.kaplenkov.avitomuzik.presentation.viewmodel.PlayerViewModel
import alex.kaplenkov.avitomuzik.ui.theme.AvitoMuzikTheme
import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val deezerViewModel: DeezerViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                0
//            )
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.FOREGROUND_SERVICE,
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                1
            )
        }
        setContent {
            AvitoMuzikTheme {
                MainScreen(deezerViewModel, playerViewModel)
            }
        }
    }
}

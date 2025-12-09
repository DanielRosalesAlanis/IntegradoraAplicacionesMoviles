package mx.edu.utez.integradoraaplicacionesmoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mx.edu.utez.integradoraaplicacionesmoviles.player.MusicPlayer
import mx.edu.utez.integradoraaplicacionesmoviles.sensor.Gesture
import mx.edu.utez.integradoraaplicacionesmoviles.sensor.GestureDetector
import mx.edu.utez.integradoraaplicacionesmoviles.sensor.ProximityManager
import mx.edu.utez.integradoraaplicacionesmoviles.ui.navigation.AppNavigation
import mx.edu.utez.integradoraaplicacionesmoviles.ui.theme.IntegradoraAplicacionesMovilesTheme
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.PlayerViewModel
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.SongViewModel

class MainActivity : ComponentActivity() {

    private lateinit var proximityManager: ProximityManager
    private lateinit var gestureDetector: GestureDetector
    private lateinit var musicPlayer: MusicPlayer
    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instancias principales
        proximityManager = ProximityManager(this)
        gestureDetector = GestureDetector()
        musicPlayer = MusicPlayer(this)
        playerViewModel = PlayerViewModel()

        // Observadores
        observeProximitySensor()
        observeGestures()

        setContent {
            IntegradoraAplicacionesMovilesTheme {
                val songViewModel = SongViewModel(this)
AppNavigation(
                    playerViewModel = playerViewModel,
                    songViewModel = songViewModel,
                    onPlayPause = {
                        android.util.Log.d("MainActivity", "onPlayPause button clicked")
                        if (musicPlayer.isPlaying()) {
                            android.util.Log.d("MainActivity", "Pausing from button")
                            musicPlayer.pause()
                            playerViewModel.markPaused()
                        } else {
                            android.util.Log.d("MainActivity", "Resuming from button")
                            playerViewModel.getCurrentSong()?.let {
                                musicPlayer.resume()
                                playerViewModel.markPlaying()
                            }
                        }
                    },
                    onNext = {
                        playerViewModel.getCurrentSong()?.let {
                            musicPlayer.play(it)
                            playerViewModel.markPlaying()
                        }
                    },
                    onPrevious = {
                        playerViewModel.getCurrentSong()?.let {
                            musicPlayer.play(it)
                            playerViewModel.markPlaying()
                        }
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        proximityManager.start()
    }

    override fun onPause() {
        super.onPause()
        proximityManager.stop()
    }

    private fun observeProximitySensor() {
        lifecycleScope.launch {
            proximityManager.isNear.collectLatest { isNear ->
                gestureDetector.onProximityChanged(isNear)
            }
        }
    }

    private fun observeGestures() {
        lifecycleScope.launch {
            gestureDetector.gesture.collectLatest { gesture ->
                if (gesture == Gesture.Toggle) {
                    val isCurrentlyPlaying = musicPlayer.isPlaying()
                    android.util.Log.d("MainActivity", "Toggle gesture, isPlaying=$isCurrentlyPlaying")
                    
                    if (isCurrentlyPlaying) {
                        android.util.Log.d("MainActivity", "Pausing")
                        musicPlayer.pause()
                        playerViewModel.markPaused()
                    } else {
                        android.util.Log.d("MainActivity", "Playing")
                        playerViewModel.getCurrentSong()?.let {
                            musicPlayer.resume()
                            playerViewModel.markPlaying()
                        } ?: android.util.Log.e("MainActivity", "No song")
                    }
                }
            }
        }
    }
}

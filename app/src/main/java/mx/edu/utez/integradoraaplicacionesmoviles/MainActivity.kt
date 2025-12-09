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
    
    private var isPlaying = false

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
                        android.util.Log.d("MainActivity", "Button clicked")
                        togglePlayPauseState()
                    },
                    onNext = {
                        playerViewModel.getCurrentSong()?.let {
                            musicPlayer.play(it)
                            playerViewModel.markPlaying()
                            isPlaying = true
                        }
                    },
                    onPrevious = {
                        playerViewModel.getCurrentSong()?.let {
                            musicPlayer.play(it)
                            playerViewModel.markPlaying()
                            isPlaying = true
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
            gestureDetector.gesture.collect { gesture ->
                if (gesture == Gesture.Tap) {
                    android.util.Log.d("MainActivity", "Sensor Tap detected")
                    startCountdown()
                }
            }
        }
    }

    private fun togglePlayPauseState() {
        android.util.Log.d("MainActivity", "togglePlayPauseState - Before: isPlaying=$isPlaying")
        
        if (isPlaying) {
            musicPlayer.pause()
            playerViewModel.markPaused()
            isPlaying = false
        } else {
            musicPlayer.resume()
            playerViewModel.markPlaying()
            isPlaying = true
        }
        
        android.util.Log.d("MainActivity", "togglePlayPauseState - After: isPlaying=$isPlaying")
    }
    
    private fun startCountdown() {
        lifecycleScope.launch {
            android.util.Log.d("MainActivity", "Starting countdown")
            
            for (i in 3 downTo 1) {
                playerViewModel.setCountdown(i)
                kotlinx.coroutines.delay(1000)
            }
            playerViewModel.setCountdown(0)
            
            togglePlayPauseState()
        }
    }
}

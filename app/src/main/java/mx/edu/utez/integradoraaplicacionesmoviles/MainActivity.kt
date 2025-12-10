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
        observeCurrentSong()

        setContent {
            IntegradoraAplicacionesMovilesTheme {
                val songViewModel = SongViewModel(this)
                AppNavigation(
                    playerViewModel = playerViewModel,
                    songViewModel = songViewModel,
                    onPlayPause = {
                        android.util.Log.d("MainActivity", "Botón presionado")
                        togglePlayPauseState()
                    },
                    onNext = {
                        playerViewModel.nextSong()
                    },
                    onPrevious = {
                        playerViewModel.previousSong()
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
                    android.util.Log.d("MainActivity", "Gesto detectado por sensor")
                    startCountdown()
                }
            }
        }
    }

    private fun togglePlayPauseState() {
        android.util.Log.d("MainActivity", "togglePlayPauseState - Antes: isPlaying=$isPlaying")
        
        if (isPlaying) {
            musicPlayer.pause()
            playerViewModel.markPaused()
            isPlaying = false
        } else {
            musicPlayer.resume()
            playerViewModel.markPlaying()
            isPlaying = true
        }
        
        android.util.Log.d("MainActivity", "togglePlayPauseState - Después: isPlaying=$isPlaying")
    }
    
    private fun observeCurrentSong() {
        var isFirstLoad = true
        lifecycleScope.launch {
            playerViewModel.currentSong.collect { song ->
                if (song != null && !isFirstLoad) {
                    android.util.Log.d("MainActivity", "Nueva canción seleccionada: ${song.name}")
                    musicPlayer.play(song)
                    playerViewModel.markPlaying()
                    isPlaying = true
                }
                if (song != null) {
                    isFirstLoad = false
                }
            }
        }
    }
    
    private fun startCountdown() {
        lifecycleScope.launch {
            android.util.Log.d("MainActivity", "Iniciando cuenta regresiva")
            
            for (i in 3 downTo 1) {
                playerViewModel.setCountdown(i)
                kotlinx.coroutines.delay(1000)
            }
            playerViewModel.setCountdown(0)
            
            togglePlayPauseState()
        }
    }
}

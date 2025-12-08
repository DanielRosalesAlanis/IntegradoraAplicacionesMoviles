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
                AppNavigation(playerViewModel)
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
                when (gesture) {
                    Gesture.Tap -> {
                        playerViewModel.nextSong()
                        playerViewModel.getCurrentSong()?.let {
                            musicPlayer.play(it)
                            playerViewModel.markPlaying()
                        }
                    }
                    Gesture.DoubleTap -> {
                        musicPlayer.pause()
                        playerViewModel.markPaused()
                    }
                    Gesture.TripleTap -> {
                        playerViewModel.previousSong()
                        playerViewModel.getCurrentSong()?.let {
                            musicPlayer.play(it)
                            playerViewModel.markPlaying()
                        }
                    }
                    Gesture.Hold -> {
                        musicPlayer.stop()
                        playerViewModel.markPaused()
                    }

                    Gesture.None -> Unit
                }

            }
        }
    }
}

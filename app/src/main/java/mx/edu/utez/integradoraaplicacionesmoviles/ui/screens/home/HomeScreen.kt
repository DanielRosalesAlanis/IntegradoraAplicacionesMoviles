package mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.PlayerViewModel

@Composable
fun HomeScreen(
    playerViewModel: PlayerViewModel,
    onNavigateToSongs: () -> Unit
) {
    val currentSong = playerViewModel.getCurrentSong()
    val isPlaying by playerViewModel.isPlaying.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Reproductor por Gestos", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(40.dp))

        if (currentSong != null) {
            Text(text = "Canción actual:", style = MaterialTheme.typography.titleMedium)
            Text(text = currentSong.title, style = MaterialTheme.typography.titleLarge)
            Text(text = currentSong.artist, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (isPlaying) "Reproduciendo..." else "Pausado",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.Center) {

                Button(onClick = {
                    playerViewModel.previousSong()
                }) { Text("Anterior") }

                Spacer(modifier = Modifier.width(10.dp))

                Button(onClick = {
                    if (isPlaying) {
                        playerViewModel.markPaused()
                    } else {
                        playerViewModel.markPlaying()
                    }
                }) {
                    Text(if (isPlaying) "Pausar" else "Reproducir")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(onClick = {
                    playerViewModel.nextSong()
                }) { Text("Siguiente") }
            }
        } else {
            Text(text = "No hay canción seleccionada")
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = onNavigateToSongs) {
            Text("Mis Canciones")
        }
    }
}

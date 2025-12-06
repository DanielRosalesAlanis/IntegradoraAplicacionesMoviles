package mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.songform

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongFormScreen(
    viewModel: SongViewModel,
    songId: Int?,
    onFinish: () -> Unit
) {
    val songs by viewModel.songs.collectAsState()
    val song = songs.find { it.id == songId }

    var title by remember { mutableStateOf(song?.title ?: "") }
    var artist by remember { mutableStateOf(song?.artist ?: "") }
    var duration by remember { mutableStateOf(song?.duration?.toString() ?: "") }
    var imageUrl by remember { mutableStateOf(song?.imageUrl ?: "") }
    var audioUrl by remember { mutableStateOf(song?.audioUrl ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (songId == null) "Nueva Canción" else "Editar Canción") })
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = artist,
                onValueChange = { artist = it },
                label = { Text("Artista") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duración (segundos)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("URL Imagen (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = audioUrl,
                onValueChange = { audioUrl = it },
                label = { Text("URL Audio") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val songObj = Song(
                        id = songId ?: 0,
                        title = title,
                        artist = artist,
                        duration = duration.toIntOrNull() ?: 0,
                        imageUrl = imageUrl,
                        audioUrl = audioUrl
                    )

                    if (songId == null) {
                        viewModel.insertSong(songObj)
                    } else {
                        viewModel.updateSong(songObj)
                    }

                    onFinish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
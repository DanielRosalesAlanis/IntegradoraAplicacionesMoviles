package mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.songform

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.SongViewModel
import mx.edu.utez.integradoraaplicacionesmoviles.util.FilePickerHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongFormScreen(
    viewModel: SongViewModel,
    songId: Int?,
    onFinish: () -> Unit
) {
    val songs by viewModel.songs.collectAsState()
    val song = songs.find { it.id == songId }

    val context = LocalContext.current
    var name by remember(song) { mutableStateOf(song?.name ?: "") }
    var artist by remember(song) { mutableStateOf(song?.artist ?: "") }
    var year by remember(song) { mutableStateOf(song?.year?.toString() ?: "") }
    var filePath by remember(song) { mutableStateOf(song?.filePath ?: "") }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    
    LaunchedEffect(songId) {
        if (songId != null) {
            viewModel.loadSongs()
        }
    }
    
    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedUri = it
            filePath = FilePickerHelper.getFileName(context, it)
        }
    }

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
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = artist,
                onValueChange = { artist = it },
                label = { Text("Artista") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Año") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Archivo: ${if (filePath.isNotEmpty()) filePath else "No seleccionado"}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (songId == null) {
                Button(
                    onClick = { filePicker.launch("audio/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Seleccionar MP3")
                }
            } else {
                Text(
                    text = "Nota: No se puede cambiar el archivo al editar",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    android.util.Log.d("SongForm", "Guardar clicked")
                    android.util.Log.d("SongForm", "name=$name, artist=$artist, year=$year")
                    android.util.Log.d("SongForm", "filePath=$filePath, uri=$selectedUri")
                    
                    if (name.isEmpty() || artist.isEmpty() || year.isEmpty()) {
                        android.util.Log.e("SongForm", "Campos vacíos")
                        return@Button
                    }
                    
                    if (songId == null && selectedUri == null) {
                        android.util.Log.e("SongForm", "No se seleccionó archivo")
                        return@Button
                    }
                    
                    val songObj = Song(
                        id = songId ?: 0,
                        name = name,
                        artist = artist,
                        year = year.toIntOrNull() ?: 2024,
                        filePath = filePath
                    )
                    android.util.Log.d("SongForm", "Song object: $songObj")

                    if (songId == null) {
                        android.util.Log.d("SongForm", "Calling insertSong")
                        viewModel.insertSong(songObj, selectedUri)
                    } else {
                        android.util.Log.d("SongForm", "Calling updateSong")
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
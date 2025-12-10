package mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.songs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song
import mx.edu.utez.integradoraaplicacionesmoviles.ui.components.SongItemCard
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.PlayerViewModel
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.SongViewModel

/**
 * Pantalla de biblioteca de canciones con diseño tipo Spotify.
 * Muestra las canciones en una cuadrícula de 2 columnas.
 */
@Composable
fun SongListScreen(
    viewModel: SongViewModel,
    playerViewModel: PlayerViewModel,
    onNavigateToForm: (Int?) -> Unit,
    onSongSelected: () -> Unit
) {
    val songs by viewModel.songs.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSongs()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Tu Biblioteca",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(songs) { song: Song ->
                        SongItemCard(
                            song = song,
                            onClick = {
                                playerViewModel.setPlaylist(songs)
                                playerViewModel.setCurrentSong(song)
                                onSongSelected()
                            },
                            onEdit = {
                                onNavigateToForm(song.id)
                            },
                            onDelete = {
                                viewModel.deleteSong(song.id)
                            }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { onNavigateToForm(null) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar canción",
                tint = Color.Black
            )
        }
    }
}

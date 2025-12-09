package mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.songs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song
import mx.edu.utez.integradoraaplicacionesmoviles.ui.components.SongItemCard
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.PlayerViewModel
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.SongViewModel

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

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToForm(null) }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            if (loading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
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
    }
}

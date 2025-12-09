package mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.integradoraaplicacionesmoviles.ui.components.*
import mx.edu.utez.integradoraaplicacionesmoviles.ui.theme.PlayerColors
import mx.edu.utez.integradoraaplicacionesmoviles.ui.theme.PlayerDimensions
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.PlayerViewModel

@Composable
fun HomeScreen(
    playerViewModel: PlayerViewModel,
    onNavigateToSongs: () -> Unit,
    onPlayPause: () -> Unit = {},
    onNext: () -> Unit = {},
    onPrevious: () -> Unit = {}
) {
    val currentSong by playerViewModel.currentSong.collectAsState()
    val isPlaying by playerViewModel.isPlaying.collectAsState()
    val countdown by playerViewModel.countdown.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PlayerDimensions.PaddingLarge.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(PlayerDimensions.PaddingXXL.dp))

            if (currentSong != null) {
                AlbumArtwork()

                Spacer(modifier = Modifier.height(PlayerDimensions.PaddingXXXL.dp))

                SongInfo(
                    songName = currentSong!!.name,
                    artistName = currentSong!!.artist
                )

                Spacer(modifier = Modifier.weight(1f))

                PlaybackControls(
                    isPlaying = isPlaying,
                    onPlayPause = onPlayPause,
                    onPrevious = {
                        playerViewModel.previousSong()
                        onPrevious()
                    },
                    onNext = {
                        playerViewModel.nextSong()
                        onNext()
                    }
                )

                Spacer(modifier = Modifier.height(PlayerDimensions.PaddingExtraLarge.dp))

                LibraryButton(onClick = onNavigateToSongs)

                Spacer(modifier = Modifier.height(PlayerDimensions.PaddingLarge.dp))
            } else {
                EmptyState(onNavigateToSongs = onNavigateToSongs)
            }
        }

        CountdownOverlay(countdown = countdown)
    }
}

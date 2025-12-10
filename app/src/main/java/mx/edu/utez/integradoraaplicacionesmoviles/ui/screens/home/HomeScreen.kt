package mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.PlayerViewModel

/**
 * Pantalla principal del reproductor de música con control por gestos.
 * Muestra la información de la canción actual y los controles de reproducción.
 */
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
            .background(Color(0xFF121212))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            if (currentSong != null) {
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF282828)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MusicNote,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        tint = Color(0xFF535353)
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = currentSong!!.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = currentSong!!.artist,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFB3B3B3),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            playerViewModel.previousSong()
                            onPrevious()
                        },
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Anterior",
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }

                    FloatingActionButton(
                        onClick = { onPlayPause() },
                        modifier = Modifier.size(72.dp),
                        containerColor = Color.White,
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                            modifier = Modifier.size(40.dp),
                            tint = Color.Black
                        )
                    }

                    IconButton(
                        onClick = {
                            playerViewModel.nextSong()
                            onNext()
                        },
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Siguiente",
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                TextButton(
                    onClick = onNavigateToSongs,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "VER BIBLIOTECA",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.MusicNote,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = Color(0xFF535353)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No hay canciones",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = onNavigateToSongs,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Text("AGREGAR CANCIONES")
                        }
                    }
                }
            }
        }

        if (countdown > 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$countdown",
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 120.sp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

package mx.edu.utez.integradoraaplicacionesmoviles.ui.components

import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.integradoraaplicacionesmoviles.ui.theme.PlayerColors
import mx.edu.utez.integradoraaplicacionesmoviles.ui.theme.PlayerDimensions

/**
 * Componente que muestra la carátula del álbum con un ícono de música.
 */
@Composable
fun AlbumArtwork(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(PlayerDimensions.AlbumArtSize.dp)
            .clip(RoundedCornerShape(PlayerDimensions.AlbumArtCornerRadius.dp))
            .background(PlayerColors.Surface),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.MusicNote,
            contentDescription = null,
            modifier = Modifier.size(PlayerDimensions.IconSizeXXXL.dp),
            tint = PlayerColors.IconTertiary
        )
    }
}

/**
 * Componente que muestra la información de la canción actual.
 */
@Composable
fun SongInfo(
    songName: String,
    artistName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = songName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = PlayerColors.TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(PlayerDimensions.PaddingSmall.dp))
        
        Text(
            text = artistName,
            style = MaterialTheme.typography.bodyLarge,
            color = PlayerColors.TextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Controles de reproducción con botones de anterior, play/pause y siguiente.
 */
@Composable
fun PlaybackControls(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onPrevious,
            modifier = Modifier.size(PlayerDimensions.ButtonSizeMedium.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = "Anterior",
                modifier = Modifier.size(PlayerDimensions.IconSizeMedium.dp),
                tint = PlayerColors.IconPrimary
            )
        }

        AnimatedPlayPauseButton(
            isPlaying = isPlaying,
            onClick = onPlayPause
        )

        IconButton(
            onClick = onNext,
            modifier = Modifier.size(PlayerDimensions.ButtonSizeMedium.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SkipNext,
                contentDescription = "Siguiente",
                modifier = Modifier.size(PlayerDimensions.IconSizeMedium.dp),
                tint = PlayerColors.IconPrimary
            )
        }
    }
}

/**
 * Botón de play/pause con animación de escala al presionar.
 */
@Composable
fun AnimatedPlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    FloatingActionButton(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = modifier
            .size(PlayerDimensions.ButtonSizeLarge.dp)
            .scale(scale),
        containerColor = PlayerColors.Secondary,
        shape = CircleShape
    ) {
        Icon(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) "Pausar" else "Reproducir",
            modifier = Modifier.size(PlayerDimensions.IconSizeMedium.dp),
            tint = PlayerColors.OnSecondary
        )
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(150)
            isPressed = false
        }
    }
}

/**
 * Overlay de cuenta regresiva que se muestra antes de ejecutar una acción.
 */
@Composable
fun CountdownOverlay(
    countdown: Int,
    modifier: Modifier = Modifier
) {
    if (countdown > 0) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(PlayerColors.OverlayDark),
            contentAlignment = Alignment.Center
        ) {
            val scale by animateFloatAsState(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 1000),
                label = "countdown_scale"
            )
            
            Text(
                text = "$countdown",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 120.sp),
                fontWeight = FontWeight.Bold,
                color = PlayerColors.TextPrimary,
                modifier = Modifier.scale(scale)
            )
        }
    }
}

/**
 * Estado vacío que se muestra cuando no hay canciones en la biblioteca.
 */
@Composable
fun EmptyState(
    onNavigateToSongs: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Outlined.MusicNote,
                contentDescription = null,
                modifier = Modifier.size(PlayerDimensions.IconSizeXXL.dp),
                tint = PlayerColors.IconTertiary
            )
            
            Spacer(modifier = Modifier.height(PlayerDimensions.PaddingMedium.dp))
            
            Text(
                text = "No hay canciones",
                style = MaterialTheme.typography.titleLarge,
                color = PlayerColors.TextPrimary
            )
            
            Spacer(modifier = Modifier.height(PlayerDimensions.PaddingExtraLarge.dp))
            
            Button(
                onClick = onNavigateToSongs,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PlayerColors.Secondary,
                    contentColor = PlayerColors.OnSecondary
                )
            ) {
                Text("AGREGAR CANCIONES")
            }
        }
    }
}

/**
 * Botón de texto para navegar a la biblioteca de canciones.
 */
@Composable
fun LibraryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "VER BIBLIOTECA",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = PlayerColors.TextPrimary,
            letterSpacing = 1.5.sp
        )
    }
}

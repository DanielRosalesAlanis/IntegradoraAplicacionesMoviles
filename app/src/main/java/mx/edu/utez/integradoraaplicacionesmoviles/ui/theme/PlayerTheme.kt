package mx.edu.utez.integradoraaplicacionesmoviles.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta de colores personalizada para el reproductor de música.
 * Inspirada en el diseño de Spotify con tonos oscuros y acentos vibrantes.
 */
object PlayerColors {
    val Background = Color(0xFF121212)
    val Surface = Color(0xFF282828)
    val SurfaceVariant = Color(0xFF181818)
    val Primary = Color(0xFF1DB954)
    val PrimaryVariant = Color(0xFF1ED760)
    val Secondary = Color(0xFFFFFFFF)
    val OnPrimary = Color(0xFF000000)
    val OnSecondary = Color(0xFF000000)
    val OnBackground = Color(0xFFFFFFFF)
    val OnSurface = Color(0xFFFFFFFF)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFB3B3B3)
    val TextTertiary = Color(0xFF535353)
    val IconPrimary = Color(0xFFFFFFFF)
    val IconSecondary = Color(0xFFB3B3B3)
    val IconTertiary = Color(0xFF535353)
    val Divider = Color(0xFF282828)
    val Error = Color(0xFFCF6679)
    val OverlayDark = Color(0xB3000000)
}

/**
 * Dimensiones estandarizadas para mantener consistencia en el diseño.
 */
object PlayerDimensions {
    const val PaddingExtraSmall = 4
    const val PaddingSmall = 8
    const val PaddingMedium = 16
    const val PaddingLarge = 24
    const val PaddingExtraLarge = 32
    const val PaddingXXL = 40
    const val PaddingXXXL = 48
    
    const val AlbumArtSize = 280
    const val AlbumArtCornerRadius = 8
    const val IconSizeSmall = 24
    const val IconSizeMedium = 40
    const val IconSizeLarge = 48
    const val IconSizeXL = 64
    const val IconSizeXXL = 80
    const val IconSizeXXXL = 120
    
    const val ButtonSizeSmall = 48
    const val ButtonSizeMedium = 56
    const val ButtonSizeLarge = 72
    
    const val ElevationSmall = 2
    const val ElevationMedium = 4
    const val ElevationLarge = 8
}

/**
 * Valores de opacidad para diferentes estados y overlays.
 */
object PlayerOpacity {
    const val Disabled = 0.38f
    const val Medium = 0.6f
    const val Overlay = 0.7f
    const val Full = 1.0f
}

/**
 * Duraciones de animación en milisegundos.
 */
object PlayerAnimations {
    const val Fast = 150
    const val Normal = 300
    const val Slow = 500
}

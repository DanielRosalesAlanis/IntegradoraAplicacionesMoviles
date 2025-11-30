package mx.edu.utez.integradoraaplicacionesmoviles.domain.model

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val duration: Int,
    val imageUrl: String?,
    val audioUrl: String?
)

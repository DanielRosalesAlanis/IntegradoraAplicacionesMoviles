package mx.edu.utez.integradoraaplicacionesmoviles.domain.model

data class Song(
    val id: Int,
    val name: String,
    val artist: String,
    val year: Int,
    val filePath: String
)

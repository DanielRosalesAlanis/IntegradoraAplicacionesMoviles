package mx.edu.utez.integradoraaplicacionesmoviles.data.remote.dto

import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

data class SongDto(
    val id: Int,
    val title: String,
    val artist: String,
    val duration: Int,
    val imageUrl: String?,
    val audioUrl: String?
)

fun SongDto.toDomain() = Song(
    id = id,
    title = title,
    artist = artist,
    duration = duration,
    imageUrl = imageUrl,
    audioUrl = audioUrl
)

fun Song.toDto() = SongDto(
    id = id,
    title = title,
    artist = artist,
    duration = duration,
    imageUrl = imageUrl,
    audioUrl = audioUrl
)
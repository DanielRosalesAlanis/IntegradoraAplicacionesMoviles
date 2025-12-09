package mx.edu.utez.integradoraaplicacionesmoviles.data.remote.dto

import com.google.gson.annotations.SerializedName
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

data class SongDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("year") val year: Int,
    @SerializedName("file_path") val filePath: String? = null
)

fun SongDto.toDomain(): Song {
    return Song(
        id = id ?: 0,
        name = name,
        artist = artist,
        year = year,
        filePath = filePath ?: ""
    )
}

fun Song.toDto(): SongDto {
    return SongDto(
        id = id,
        name = name,
        artist = artist,
        year = year,
        filePath = filePath
    )
}

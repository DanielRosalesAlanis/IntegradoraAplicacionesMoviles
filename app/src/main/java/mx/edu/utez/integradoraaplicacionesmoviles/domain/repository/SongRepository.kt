package mx.edu.utez.integradoraaplicacionesmoviles.domain.repository

import android.net.Uri
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

interface SongRepository {
    suspend fun getSongs(): List<Song>
    suspend fun insert(song: Song, fileUri: Uri? = null): Song
    suspend fun update(song: Song): Song
    suspend fun delete(id: Int)
}
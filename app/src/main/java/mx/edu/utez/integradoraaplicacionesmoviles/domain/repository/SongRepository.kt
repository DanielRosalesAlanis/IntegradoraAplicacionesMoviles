package mx.edu.utez.integradoraaplicacionesmoviles.domain.repository

import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

interface SongRepository {
    suspend fun getSongs(): List<Song>
    suspend fun insert(song: Song): Song
    suspend fun update(song: Song): Song
    suspend fun delete(id: Int)
}
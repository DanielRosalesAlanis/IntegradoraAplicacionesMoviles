package mx.edu.utez.integradoraaplicacionesmoviles.data.repository


import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.api.SongApi
import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.dto.toDomain
import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.dto.toDto
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song
import mx.edu.utez.integradoraaplicacionesmoviles.domain.repository.SongRepository

class SongRepositoryImpl(
    private val api: SongApi
) : SongRepository {

    override suspend fun getSongs(): List<Song> {
        return api.getSongs().map { it.toDomain() }
    }

    override suspend fun insert(song: Song): Song {
        return api.insert(song.toDto()).toDomain()
    }

    override suspend fun update(song: Song): Song {
        return api.update(song.id, song.toDto()).toDomain()
    }

    override suspend fun delete(id: Int) {
        api.delete(id)
    }
}

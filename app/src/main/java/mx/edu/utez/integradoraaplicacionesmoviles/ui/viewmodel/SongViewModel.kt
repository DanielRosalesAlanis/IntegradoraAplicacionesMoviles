package mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.api.RetrofitClient
import mx.edu.utez.integradoraaplicacionesmoviles.data.repository.SongRepositoryImpl
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

class SongViewModel(private val context: android.content.Context) : ViewModel() {

    private val repository = SongRepositoryImpl(RetrofitClient.api, context)

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadSongs() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _songs.value = repository.getSongs()
            } catch (e: Exception) {
                e.printStackTrace()
                _songs.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    fun insertSong(song: Song, fileUri: android.net.Uri?) {
        android.util.Log.d("SongViewModel", "insertSong llamado: $song, uri: $fileUri")
        viewModelScope.launch {
            try {
                android.util.Log.d("SongViewModel", "Llamando repository.insert...")
                val result = repository.insert(song, fileUri)
                android.util.Log.d("SongViewModel", "Insertar exitoso: $result")
                loadSongs()
            } catch (e: Exception) {
                android.util.Log.e("SongViewModel", "Error al insertar", e)
                e.printStackTrace()
            }
        }
    }

    fun updateSong(song: Song) {
        viewModelScope.launch {
            try {
                repository.update(song)
                loadSongs()
            } catch (e: Exception) {
                android.util.Log.e("SongViewModel", "Error al actualizar", e)
                e.printStackTrace()
            }
        }
    }

    fun deleteSong(id: Int) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                loadSongs()
            } catch (e: Exception) {
                android.util.Log.e("SongViewModel", "Error al eliminar", e)
                e.printStackTrace()
            }
        }
    }
}
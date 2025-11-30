package mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.api.RetrofitClient
import mx.edu.utez.integradoraaplicacionesmoviles.data.repository.SongRepositoryImpl
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

class SongViewModel : ViewModel() {

    private val repository = SongRepositoryImpl(RetrofitClient.api)

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadSongs() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _songs.value = repository.getSongs()
            } finally {
                _loading.value = false
            }
        }
    }

    fun insertSong(song: Song) {
        viewModelScope.launch {
            repository.insert(song)
            loadSongs()
        }
    }

    fun updateSong(song: Song) {
        viewModelScope.launch {
            repository.update(song)
            loadSongs()
        }
    }

    fun deleteSong(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
            loadSongs()
        }
    }
}
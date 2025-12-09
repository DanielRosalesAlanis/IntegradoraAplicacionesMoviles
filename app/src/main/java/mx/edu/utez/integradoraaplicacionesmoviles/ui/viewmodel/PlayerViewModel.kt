package mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

class PlayerViewModel : ViewModel() {

    private var playlist: List<Song> = emptyList()
    private var currentIndex = 0

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    fun setPlaylist(songs: List<Song>) {
        playlist = songs
        currentIndex = 0
    }

    fun setCurrentSong(song: Song) {
        val index = playlist.indexOf(song)
        if (index != -1) {
            currentIndex = index
            _currentSong.value = song
        }
    }

    fun getCurrentSong(): Song? {
        return _currentSong.value
    }

    private fun updateCurrentSong() {
        _currentSong.value = if (playlist.isNotEmpty()) playlist[currentIndex] else null
    }

    fun markPlaying() {
        _isPlaying.value = true
    }

    fun markPaused() {
        _isPlaying.value = false
    }

    fun nextSong() {
        if (playlist.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % playlist.size
            updateCurrentSong()
        }
    }

    fun previousSong() {
        if (playlist.isNotEmpty()) {
            currentIndex =
                if (currentIndex > 0) currentIndex - 1 else playlist.size - 1
            updateCurrentSong()
        }
    }
}

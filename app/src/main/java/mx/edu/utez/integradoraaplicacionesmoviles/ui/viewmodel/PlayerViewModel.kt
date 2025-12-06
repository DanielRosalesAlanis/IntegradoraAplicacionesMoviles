package mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel

import androidx.lifecycle.ViewModel
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

class PlayerViewModel : ViewModel() {

    private var currentIndex = 0
    private var playlist: List<Song> = emptyList()

    fun setPlaylist(songs: List<Song>) {
        playlist = songs
    }

    fun getCurrentSong(): Song? {
        return if (playlist.isNotEmpty()) playlist[currentIndex] else null
    }

    fun nextSong() {
        if (playlist.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % playlist.size
        }
    }

    fun previousSong() {
        if (playlist.isNotEmpty()) {
            currentIndex = if (currentIndex > 0) currentIndex - 1 else playlist.size - 1
        }
    }
}
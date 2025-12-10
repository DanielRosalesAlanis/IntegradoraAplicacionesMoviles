package mx.edu.utez.integradoraaplicacionesmoviles.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

class MusicPlayer(context: Context) {

    private val player = ExoPlayer.Builder(context).build()

    fun play(song: Song, baseUrl: String = "http://192.168.0.10:5000/") {
        player.stop()
        val url = baseUrl + "uploads/" + song.filePath
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        player.play()
        android.util.Log.d("MusicPlayer", "Playing: ${song.name}")
    }

    fun pause() {
        player.pause()
        android.util.Log.d("MusicPlayer", "Paused, isPlaying=${player.isPlaying}")
    }

    fun resume() {
        player.play()
        android.util.Log.d("MusicPlayer", "Resumed, isPlaying=${player.isPlaying}")
    }

    fun togglePlayPause() {
        if (player.isPlaying) {
            pause()
        } else {
            resume()
        }
    }

    fun isPlaying(): Boolean {
        return player.isPlaying
    }

    fun stop() {
        player.stop()
    }

    fun release() {
        player.release()
    }
}

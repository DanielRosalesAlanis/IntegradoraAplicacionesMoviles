package mx.edu.utez.integradoraaplicacionesmoviles.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song
import mx.edu.utez.integradoraaplicacionesmoviles.util.Constants

class MusicPlayer(context: Context) {

    private val player = ExoPlayer.Builder(context).build()

    fun play(song: Song) {
        player.stop()
        val url = Constants.BASE_URL + "uploads/" + song.filePath
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        player.play()
        android.util.Log.d("MusicPlayer", "Reproduciendo: ${song.name}")
    }

    fun pause() {
        player.pause()
        android.util.Log.d("MusicPlayer", "Pausado, isPlaying=${player.isPlaying}")
    }

    fun resume() {
        player.play()
        android.util.Log.d("MusicPlayer", "Reanudado, isPlaying=${player.isPlaying}")
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

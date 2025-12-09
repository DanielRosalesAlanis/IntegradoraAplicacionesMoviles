package mx.edu.utez.integradoraaplicacionesmoviles.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

class MusicPlayer(context: Context) {

    private val player = ExoPlayer.Builder(context).build()

    fun play(song: Song, baseUrl: String = "http://192.168.107.123:5000/") {
        player.stop()
        val url = baseUrl + "uploads/" + song.filePath
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        player.play()
    }

    fun pause() {
        if (player.isPlaying) {
            player.pause()
        }
    }

    fun resume() {
        if (!player.isPlaying) {
            player.play()
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

package mx.edu.utez.integradoraaplicacionesmoviles.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

class MusicPlayer(context: Context) {

    private val player = ExoPlayer.Builder(context).build()

    fun play(song: Song) {
        player.setMediaItem(MediaItem.fromUri(song.audioUrl ?: ""))
        player.prepare()
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun resume() {
        player.play()
    }

    fun stop() {
        player.stop()
    }

    fun release() {
        player.release()
    }
}

package mx.edu.utez.integradoraaplicacionesmoviles.data.repository

import android.content.Context
import android.net.Uri
import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.api.SongApi
import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.dto.toDomain
import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.dto.toDto
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song
import mx.edu.utez.integradoraaplicacionesmoviles.domain.repository.SongRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class SongRepositoryImpl(
    private val api: SongApi,
    private val context: Context
) : SongRepository {

    override suspend fun getSongs(): List<Song> {
        return api.getSongs().map { it.toDomain() }
    }

    override suspend fun insert(song: Song, fileUri: Uri?): Song {
        android.util.Log.d("SongRepo", "Insert called with uri: $fileUri")
        if (fileUri == null) {
            android.util.Log.e("SongRepo", "File URI is null")
            throw IllegalArgumentException("File URI is required")
        }
        
        val inputStream = context.contentResolver.openInputStream(fileUri)
        if (inputStream == null) {
            android.util.Log.e("SongRepo", "Cannot open input stream")
            throw IllegalArgumentException("Cannot open file")
        }
        
        val tempFile = File(context.cacheDir, song.filePath)
        android.util.Log.d("SongRepo", "Temp file: ${tempFile.absolutePath}")
        
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        android.util.Log.d("SongRepo", "File copied, size: ${tempFile.length()}")
        
        val requestFile = tempFile.asRequestBody("audio/*".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
        
        val nameBody = song.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val artistBody = song.artist.toRequestBody("text/plain".toMediaTypeOrNull())
        val yearBody = song.year.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        
        android.util.Log.d("SongRepo", "Calling API insert...")
        val result = api.insert(filePart, nameBody, artistBody, yearBody).toDomain()
        android.util.Log.d("SongRepo", "API insert success: $result")
        return result
    }

    override suspend fun update(song: Song): Song {
        return api.update(song.id, song.toDto()).toDomain()
    }

    override suspend fun delete(id: Int) {
        api.delete(id)
    }
}

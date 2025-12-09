package mx.edu.utez.integradoraaplicacionesmoviles.data.remote.api

import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.dto.SongDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface SongApi {

    @GET("songs")
    suspend fun getSongs(): List<SongDto>

    @Multipart
    @POST("songs")
    suspend fun insert(
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("artist") artist: RequestBody,
        @Part("year") year: RequestBody
    ): SongDto

    @PUT("songs/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body song: SongDto
    ): SongDto

    @DELETE("songs/{id}")
    suspend fun delete(@Path("id") id: Int)
}

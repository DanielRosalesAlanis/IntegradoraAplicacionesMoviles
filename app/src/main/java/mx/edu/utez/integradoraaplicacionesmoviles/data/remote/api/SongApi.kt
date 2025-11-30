package mx.edu.utez.integradoraaplicacionesmoviles.data.remote.api


import mx.edu.utez.integradoraaplicacionesmoviles.data.remote.dto.SongDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SongApi {

    @GET("songs")
    suspend fun getSongs(): List<SongDto>

    @POST("songs")
    suspend fun insert(@Body song: SongDto): SongDto

    @PUT("songs/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body song: SongDto
    ): SongDto

    @DELETE("songs/{id}")
    suspend fun delete(@Path("id") id: Int)
}

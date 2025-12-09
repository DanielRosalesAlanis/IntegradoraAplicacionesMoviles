package mx.edu.utez.integradoraaplicacionesmoviles.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.107.123:5000/"

    val api: SongApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SongApi::class.java)
    }
}

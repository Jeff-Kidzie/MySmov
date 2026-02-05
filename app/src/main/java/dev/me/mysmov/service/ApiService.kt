package dev.me.mysmov.service

import dev.me.mysmov.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/discover/movie")
    suspend fun getDiscoverMovie(): Response<List<Movie>>
}

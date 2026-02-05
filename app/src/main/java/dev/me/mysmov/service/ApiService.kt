package dev.me.mysmov.service

import dev.me.mysmov.data.DataResponse
import dev.me.mysmov.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("discover/movie")
    suspend fun getDiscoverMovie(): Response<List<Movie>>

    @GET("movie/now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovie(): Response<DataResponse<Movie>>
}

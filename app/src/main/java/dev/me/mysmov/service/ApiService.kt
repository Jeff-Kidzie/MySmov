package dev.me.mysmov.service

import dev.me.mysmov.data.DataResponse
import dev.me.mysmov.data.model.CastUi
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.data.model.MovieDetail
import dev.me.mysmov.data.model.dto.CastDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("discover/movie")
    suspend fun getDiscoverMovie(): Response<List<Movie>>

    @GET("movie/now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovie(): Response<DataResponse<Movie>>

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): Response<MovieDetail>

    @GET("movie/{movie_id}/similar?language=en-US&page=1")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Int): Response<DataResponse<Movie>>

    @GET("movie/{movie_id}/credits?language=en-US")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int): Response<DataResponse<CastDto>>
}

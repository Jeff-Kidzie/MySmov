package dev.me.mysmov.data.remote

import dev.me.mysmov.data.DataResponse
import dev.me.mysmov.data.model.dto.CastDto
import dev.me.mysmov.data.model.dto.VideoTrailerDto
import dev.me.mysmov.domain.model.MediaItem
import dev.me.mysmov.domain.model.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getDiscoverMovie(): Response<List<MediaItem>>

    @GET("movie/now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovie(): Response<DataResponse<MediaItem>>

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): Response<MovieDetail>

    @GET("movie/{movie_id}/similar?language=en-US&page=1")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Int): Response<DataResponse<MediaItem>>

    @GET("movie/{movie_id}/credits?language=en-US")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int): Response<DataResponse<CastDto>>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") movieId: Int): Response<DataResponse<VideoTrailerDto>>

    @GET("movie/{category}")
    suspend fun getMovieByCategory(
        @Path("category") category: String,
        @Query("page") page: Int = 1
    ): Response<DataResponse<MediaItem>>

    @GET("tv/{category}")
    suspend fun getTvByCategory(
        @Path("category") category: String,
        @Query("page") page: Int = 1
    ): Response<DataResponse<MediaItem>>
}
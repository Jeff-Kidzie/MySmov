package dev.me.mysmov.data.repository

import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.data.model.MovieDetail
import dev.me.mysmov.data.model.dto.CastDto
import dev.me.mysmov.data.model.dto.VideoTrailerDto

interface MovieRepository {
    suspend fun getDiscoverMovies() : CallResult<List<Movie>>
    suspend fun getNowPlayingMovies() : CallResult<List<Movie>>
    suspend fun getMovieDetail(id : Int) : CallResult<MovieDetail>

    suspend fun getCastByMovie(movieId : Int) : CallResult<List<CastDto>>
    suspend fun getVideosByMovie(movieId : Int) : CallResult<List<VideoTrailerDto>>
}
package dev.me.mysmov.data.repository

import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.data.model.MovieDetail

interface MovieRepository {
    suspend fun getDiscoverMovies() : CallResult<List<Movie>>
    suspend fun getNowPlayingMovies() : CallResult<List<Movie>>
    suspend fun getMovieDetail(id : Int) : CallResult<MovieDetail>
}
package dev.me.mysmov.data.repository

import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.data.model.MediaItem
import dev.me.mysmov.data.model.MovieDetail
import dev.me.mysmov.data.model.dto.CastDto
import dev.me.mysmov.data.model.dto.VideoTrailerDto

interface MovieRepository {
    suspend fun getDiscoverMovies() : CallResult<List<MediaItem>>
    suspend fun getNowPlayingMovies() : CallResult<List<MediaItem>>
    suspend fun getMovieDetail(id : Int) : CallResult<MovieDetail>

    suspend fun getCastByMovie(movieId : Int) : CallResult<List<CastDto>>
    suspend fun getVideosByMovie(movieId : Int) : CallResult<List<VideoTrailerDto>>
    suspend fun getMoviesByCategory(category : String, page : Int = 1) : CallResult<List<MediaItem>>
    suspend fun getTvByCategory(category : String, page : Int = 1) : CallResult<List<MediaItem>>
}
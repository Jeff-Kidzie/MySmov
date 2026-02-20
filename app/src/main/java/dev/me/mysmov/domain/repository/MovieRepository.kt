package dev.me.mysmov.domain.repository

import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.domain.model.MediaItem
import dev.me.mysmov.domain.model.MovieDetail
import dev.me.mysmov.domain.model.ui.Cast
import dev.me.mysmov.domain.model.ui.VideoTrailer

interface MovieRepository {
    suspend fun getDiscoverMovies() : CallResult<List<MediaItem>>
    suspend fun getNowPlayingMovies() : CallResult<List<MediaItem>>
    suspend fun getMovieDetail(id : Int) : CallResult<MovieDetail>

    suspend fun getCastByMovie(movieId : Int) : CallResult<List<Cast>>
    suspend fun getVideosByMovie(movieId : Int) : CallResult<List<VideoTrailer>>
    suspend fun getMoviesByCategory(category : String, page : Int = 1) : CallResult<List<MediaItem>>
    suspend fun getTvByCategory(category : String, page : Int = 1) : CallResult<List<MediaItem>>
}
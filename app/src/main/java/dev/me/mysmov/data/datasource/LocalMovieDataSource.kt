package dev.me.mysmov.data.datasource

import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.data.model.MediaItem
import dev.me.mysmov.data.model.MovieDetail
import dev.me.mysmov.data.model.dto.CastDto
import dev.me.mysmov.data.model.dto.VideoTrailerDto
import dev.me.mysmov.data.repository.MovieRepository

class LocalMovieDataSource : MovieRepository {
    override suspend fun getDiscoverMovies(): CallResult<List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNowPlayingMovies(): CallResult<List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(id: Int): CallResult<MovieDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun getCastByMovie(movieId: Int): CallResult<List<CastDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getVideosByMovie(movieId: Int): CallResult<List<VideoTrailerDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesByCategory(
        category: String,
        page: Int
    ): CallResult<List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvByCategory(
        category: String,
        page: Int
    ): CallResult<List<MediaItem>> {
        TODO("Not yet implemented")
    }
}
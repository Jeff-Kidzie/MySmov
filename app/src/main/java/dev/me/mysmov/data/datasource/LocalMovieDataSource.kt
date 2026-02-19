package dev.me.mysmov.data.datasource

import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.domain.model.MediaItem
import dev.me.mysmov.domain.model.MovieDetail
import dev.me.mysmov.data.local.dao.AppDatabase
import dev.me.mysmov.data.model.dto.CastDto
import dev.me.mysmov.data.model.dto.VideoTrailerDto
import dev.me.mysmov.domain.repository.MovieRepository

class LocalMovieDataSource(val database: AppDatabase) : MovieRepository {
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
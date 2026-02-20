package dev.me.mysmov.data.datasource

import dev.me.mysmov.core.AppConstant
import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.core.network.callApi
import dev.me.mysmov.core.network.transform
import dev.me.mysmov.domain.model.MediaItem
import dev.me.mysmov.domain.model.MovieDetail
import dev.me.mysmov.data.model.dto.CastDto
import dev.me.mysmov.data.model.dto.VideoTrailerDto
import dev.me.mysmov.data.model.dto.toCast
import dev.me.mysmov.data.model.dto.toVideoTrailer
import dev.me.mysmov.domain.repository.MovieRepository
import dev.me.mysmov.data.remote.ApiService
import dev.me.mysmov.domain.model.ui.Cast
import dev.me.mysmov.domain.model.ui.VideoTrailer
import kotlin.collections.filter

class RemoteMovieDataSource(private val apiService: ApiService) :
    MovieRepository {
    override suspend fun getDiscoverMovies(): CallResult<List<MediaItem>> {
        val result = callApi { apiService.getDiscoverMovie() }
        return result
    }

    override suspend fun getNowPlayingMovies(): CallResult<List<MediaItem>> {
        return callApi { apiService.getNowPlayingMovie() }.transform { dataResponse ->
            dataResponse.results.map { movie ->
                MediaItem(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    posterPath = if (movie.posterPath.startsWith("http")) {
                        movie.posterPath
                    } else {
                        AppConstant.BASE_URL_IMAGE + movie.posterPath
                    },
                    rating = movie.rating,
                    backdropPath = AppConstant.BASE_URL_IMAGE + movie.backdropPath,
                    releaseDate = movie.releaseDate
                )
            }
        }
    }

    override suspend fun getMovieDetail(id: Int): CallResult<MovieDetail> {
        return callApi { apiService.getMovieDetail(id) }.transform { movieDetail ->
            movieDetail.copy(
                posterPath = AppConstant.BASE_URL_IMAGE + movieDetail.posterPath,
                backdropPath = AppConstant.BASE_URL_IMAGE + movieDetail.backdropPath
            )
        }
    }

    override suspend fun getCastByMovie(movieId: Int): CallResult<List<Cast>> {
        return callApi { apiService.getMovieCredits(movieId) }.transform { dataResponse ->
            dataResponse.results.map { it.toCast() }
        }
    }

    override suspend fun getVideosByMovie(movieId: Int): CallResult<List<VideoTrailer>> {
        return callApi { apiService.getMovieVideos(movieId) }.transform { dataResponse ->
            dataResponse.results.filter { it.site == "YouTube" && (it.type == "Trailer") }
                .map {
                    it.toVideoTrailer()
                }
        }
    }

    override suspend fun getMoviesByCategory(
        category: String,
        page: Int
    ): CallResult<List<MediaItem>> {
        return callApi {
            apiService.getMovieByCategory(
                category,
                page
            )
        }.transform { dataResponse ->
            dataResponse.results.map { movie ->
                MediaItem(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    posterPath = if (movie.posterPath.startsWith("http")) {
                        movie.posterPath
                    } else {
                        AppConstant.BASE_URL_IMAGE + movie.posterPath
                    },
                    rating = movie.rating,
                    backdropPath = AppConstant.BASE_URL_IMAGE + movie.backdropPath,
                    releaseDate = movie.releaseDate
                )
            }
        }
    }

    override suspend fun getTvByCategory(
        category: String,
        page: Int
    ): CallResult<List<MediaItem>> {
        TODO("Not yet implemented")
    }
}
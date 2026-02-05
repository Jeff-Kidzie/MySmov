package dev.me.mysmov.data.datasource

import dev.me.mysmov.core.AppConstant
import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.core.network.callApi
import dev.me.mysmov.core.network.transform
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.data.repository.MovieRepository
import dev.me.mysmov.service.ApiService

class RemoteMovieDataSource(private val apiService: ApiService) : MovieRepository {
    override suspend fun getDiscoverMovies(): CallResult<List<Movie>> {
        val result = callApi { apiService.getDiscoverMovie() }
        return result
    }

    override suspend fun getNowPlayingMovies(): CallResult<List<Movie>> {
        return callApi { apiService.getNowPlayingMovie() }.transform { dataResponse ->
            dataResponse.results.map { movie ->
                Movie(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    posterPath = if (movie.posterPath.startsWith("http")) {
                        movie.posterPath
                    } else {
                        AppConstant.BASE_URL_IMAGE + movie.posterPath
                    },
                    rating = movie.rating,
                    backdropPath = movie.backdropPath,
                    releaseDate = movie.releaseDate
                )
            }
        }
    }
}
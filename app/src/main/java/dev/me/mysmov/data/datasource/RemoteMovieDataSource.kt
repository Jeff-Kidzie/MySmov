package dev.me.mysmov.data.datasource

import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.core.network.callApi
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.data.repository.MovieRepository
import dev.me.mysmov.service.ApiService

class RemoteMovieDataSource(private val apiService: ApiService) : MovieRepository {
    override suspend fun getDiscoverMovies(): CallResult<List<Movie>> {
        val result = callApi { apiService.getDiscoverMovie() }
        return result
    }
}
package dev.me.mysmov.data.datasource

import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.data.repository.MovieRepository

class LocalMovieDataSource : MovieRepository {
    override suspend fun getDiscoverMovies(): CallResult<List<Movie>> {
        TODO("Not yet implemented")
    }
}
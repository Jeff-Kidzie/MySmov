package dev.me.mysmov.domain

import dev.me.mysmov.core.base.BaseUseCase
import dev.me.mysmov.core.base.UseCaseParam
import dev.me.mysmov.core.base.UseCaseResult
import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.data.repository.MovieRepository

class MovieUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<MovieUseCaseParam, MovieUseCaseResult> {
    override suspend fun execute(param: MovieUseCaseParam): MovieUseCaseResult {
        return when (val result = movieRepository.getDiscoverMovies()) {
            is CallResult.Error -> MovieUseCaseResult.Error(result.message)
            is CallResult.Exception -> MovieUseCaseResult.Error(result.exception.message.toString())
            is CallResult.Success -> MovieUseCaseResult.Success(result.data)
        }
    }
}

object MovieUseCaseParam : UseCaseParam
sealed class MovieUseCaseResult : UseCaseResult {
    data class Success(val movies: List<Movie>) : MovieUseCaseResult()
    data class Error(val message: String) : MovieUseCaseResult()
}
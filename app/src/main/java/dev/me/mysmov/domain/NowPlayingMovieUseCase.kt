package dev.me.mysmov.domain

import dev.me.mysmov.core.base.BaseUseCase
import dev.me.mysmov.core.base.UseCaseParam
import dev.me.mysmov.core.base.UseCaseResult
import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.data.repository.MovieRepository

class NowPlayingMovieUseCase(val movieRepository: MovieRepository) :
    BaseUseCase<NowPlayingMovieUseCaseParam, NowPlayingMovieUseCaseResult> {
    override suspend fun execute(param: NowPlayingMovieUseCaseParam): NowPlayingMovieUseCaseResult {
        return when (val result = movieRepository.getNowPlayingMovies()) {
            is CallResult.Success -> NowPlayingMovieUseCaseResult.Success(result.data)
            is CallResult.Error -> NowPlayingMovieUseCaseResult.Error(result.message)
            is CallResult.Exception -> NowPlayingMovieUseCaseResult.Error(result.exception.message.toString())
        }
    }
}

object NowPlayingMovieUseCaseParam : UseCaseParam
sealed class NowPlayingMovieUseCaseResult : UseCaseResult {
    data class Success(val movies: List<dev.me.mysmov.data.model.Movie>) :
        NowPlayingMovieUseCaseResult()

    data class Error(val message: String) : NowPlayingMovieUseCaseResult()
}
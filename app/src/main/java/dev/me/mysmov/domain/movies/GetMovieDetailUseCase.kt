package dev.me.mysmov.domain.movies

import dev.me.mysmov.core.base.BaseUseCase
import dev.me.mysmov.core.base.UseCaseParam
import dev.me.mysmov.core.base.UseCaseResult
import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.data.model.MovieDetail
import dev.me.mysmov.data.repository.MovieRepository

class GetMovieDetailUseCase(private val movieRepository: MovieRepository) : BaseUseCase<GetMovieDetailUseCaseParam, GetMovieDetailUseCaseResult> {
    override suspend fun execute(param: GetMovieDetailUseCaseParam): GetMovieDetailUseCaseResult {
        return when(val result = movieRepository.getMovieDetail(param.id)){
            is CallResult.Error -> GetMovieDetailUseCaseResult.Error(result.message)
            is CallResult.Exception -> GetMovieDetailUseCaseResult.Error(result.exception.message.toString())
            is CallResult.Success -> GetMovieDetailUseCaseResult.Success(result.data)
        }
    }
}

data class GetMovieDetailUseCaseParam(val id : Int) : UseCaseParam
sealed class GetMovieDetailUseCaseResult : UseCaseResult {
    data class Success(val movieDetail : MovieDetail) : GetMovieDetailUseCaseResult()
    data class Error(val message : String) : GetMovieDetailUseCaseResult()
}
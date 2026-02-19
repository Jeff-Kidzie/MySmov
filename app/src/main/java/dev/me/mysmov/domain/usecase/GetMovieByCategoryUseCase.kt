package dev.me.mysmov.domain.usecase

import dev.me.mysmov.core.base.BaseUseCase
import dev.me.mysmov.core.base.UseCaseParam
import dev.me.mysmov.core.base.UseCaseResult
import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.domain.model.MediaItem
import dev.me.mysmov.domain.model.MovieCategory
import dev.me.mysmov.domain.repository.MovieRepository

//Todo : Add pagination as parameter
class GetMovieByCategoryUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<GetMovieByCategoryUseCaseParam, GetMovieByCategoryUseCaseResult> {
    override suspend fun execute(param: GetMovieByCategoryUseCaseParam): GetMovieByCategoryUseCaseResult {
        return when (val result = movieRepository.getMoviesByCategory(
            category = param.category.name,
            page = param.page
        )) {
            is CallResult.Error -> GetMovieByCategoryUseCaseResult.Error(result.message)
            is CallResult.Exception -> GetMovieByCategoryUseCaseResult.Error(
                result.exception.message.toString()
            )

            is CallResult.Success -> GetMovieByCategoryUseCaseResult.Success(
                result.data
            )
        }
    }
}

data class GetMovieByCategoryUseCaseParam(
    val category: MovieCategory, val page: Int = 1
) : UseCaseParam

sealed class GetMovieByCategoryUseCaseResult : UseCaseResult {
    data class Success(val mediaItems: List<MediaItem>) :
        GetMovieByCategoryUseCaseResult()

    data class Error(val message: String) : GetMovieByCategoryUseCaseResult()
}
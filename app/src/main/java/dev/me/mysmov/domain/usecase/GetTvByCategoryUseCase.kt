package dev.me.mysmov.domain.usecase

import dev.me.mysmov.core.base.BaseUseCase
import dev.me.mysmov.core.base.UseCaseParam
import dev.me.mysmov.core.base.UseCaseResult
import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.domain.model.MediaItem
import dev.me.mysmov.domain.model.TvCategory
import dev.me.mysmov.domain.repository.MovieRepository

class GetTvByCategoryUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<GetTvByCategoryUseCaseParam, GetTvByCategoryUseCaseResult> {
    override suspend fun execute(param: GetTvByCategoryUseCaseParam): GetTvByCategoryUseCaseResult {
        return when (val result = movieRepository.getTvByCategory(
            category = param.category.name,
            page = param.page,
        )) {
            is CallResult.Error -> GetTvByCategoryUseCaseResult.Error(result.message)
            is CallResult.Exception -> GetTvByCategoryUseCaseResult.Error(
                result.exception.message.toString()
            )
            is CallResult.Success -> GetTvByCategoryUseCaseResult.Success(result.data)
        }
    }
}

data class GetTvByCategoryUseCaseParam(
    val category: TvCategory,
    val page: Int = 1,
) : UseCaseParam

sealed class GetTvByCategoryUseCaseResult : UseCaseResult {
    data class Success(val mediaItems: List<MediaItem>) : GetTvByCategoryUseCaseResult()
    data class Error(val message: String) : GetTvByCategoryUseCaseResult()
}

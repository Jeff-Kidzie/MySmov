package dev.me.mysmov.domain.usecase

import dev.me.mysmov.core.base.BaseUseCase
import dev.me.mysmov.core.base.UseCaseParam
import dev.me.mysmov.core.base.UseCaseResult
import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.domain.model.ui.CastUi
import dev.me.mysmov.domain.repository.MovieRepository
import dev.me.mysmov.domain.usecase.GetCastUseCaseResult.*

class GetCastUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<GetCastUseCaseParam, GetCastUseCaseResult> {
    override suspend fun execute(param: GetCastUseCaseParam): GetCastUseCaseResult {
        return when (val result = movieRepository.getCastByMovie(param.movieId)) {
            is CallResult.Success -> {
                val castList = result.data.map { castDto ->
                    CastUi(
                        id = castDto.id,
                        name = castDto.name,
                        role = castDto.character,
                        imgUrl = castDto.profilePath ?: ""
                    )
                }
                GetCastSuccess(castList)
            }

            is CallResult.Error -> {
                GetCastFailure(result.message)
            }

            is CallResult.Exception -> GetCastFailure(result.exception.message ?: "Unknown Error")
        }
    }

}

data class GetCastUseCaseParam(
    val movieId: Int
) : UseCaseParam

sealed class GetCastUseCaseResult : UseCaseResult {
    data class GetCastSuccess(val castList: List<CastUi>) : GetCastUseCaseResult()
    data class GetCastFailure(val errorMessage: String) : GetCastUseCaseResult()
}
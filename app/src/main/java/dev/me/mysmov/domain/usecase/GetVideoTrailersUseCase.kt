package dev.me.mysmov.domain.usecase

import dev.me.mysmov.core.base.BaseUseCase
import dev.me.mysmov.core.base.UseCaseParam
import dev.me.mysmov.core.base.UseCaseResult
import dev.me.mysmov.core.network.CallResult
import dev.me.mysmov.domain.model.ui.VideoTrailer
import dev.me.mysmov.domain.repository.MovieRepository

class GetVideoTrailersUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<GetVideoTrailersUseCaseParam, GetVideoTrailersUseCaseResult> {

    override suspend fun execute(param: GetVideoTrailersUseCaseParam): GetVideoTrailersUseCaseResult {
        return when (val result = movieRepository.getVideosByMovie(param.movieId)) {
            is CallResult.Success -> {
                val trailerList = result.data
                    .filter { it.site == "YouTube" && (it.type == "Trailer") }
                    .map { videoDto ->
                        VideoTrailer(
                            imgUrl = "https://img.youtube.com/vi/${videoDto.key}/hqdefault.jpg",
                            title = videoDto.name
                        )
                    }
                GetVideoTrailersUseCaseResult.Success(trailerList)
            }

            is CallResult.Error -> {
                GetVideoTrailersUseCaseResult.Error(result.message)
            }

            is CallResult.Exception -> {
                GetVideoTrailersUseCaseResult.Error(result.exception.message ?: "Unknown Error")
            }
        }
    }
}

data class GetVideoTrailersUseCaseParam(
    val movieId: Int
) : UseCaseParam

sealed class GetVideoTrailersUseCaseResult : UseCaseResult {
    data class Success(val trailerList: List<VideoTrailer>) : GetVideoTrailersUseCaseResult()
    data class Error(val errorMessage: String) : GetVideoTrailersUseCaseResult()
}

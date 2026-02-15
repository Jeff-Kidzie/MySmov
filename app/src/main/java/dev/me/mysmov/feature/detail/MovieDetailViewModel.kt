package dev.me.mysmov.feature.detail

import androidx.lifecycle.viewModelScope
import dev.me.mysmov.core.base.BaseViewModel
import dev.me.mysmov.data.model.ui.CastUi
import dev.me.mysmov.data.model.ui.VideoTrailerUi
import dev.me.mysmov.domain.GetCastUseCase
import dev.me.mysmov.domain.GetCastUseCaseParam
import dev.me.mysmov.domain.GetCastUseCaseResult
import dev.me.mysmov.domain.movies.GetMovieDetailUseCase
import dev.me.mysmov.domain.movies.GetMovieDetailUseCaseParam
import dev.me.mysmov.domain.movies.GetMovieDetailUseCaseResult
import dev.me.mysmov.domain.GetVideoTrailersUseCase
import dev.me.mysmov.domain.GetVideoTrailersUseCaseParam
import dev.me.mysmov.domain.GetVideoTrailersUseCaseResult
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getDetailMovieUseCase: GetMovieDetailUseCase,
    private val getCastUseCase: GetCastUseCase,
    private val getVideoTrailersUseCase: GetVideoTrailersUseCase
) :
    BaseViewModel<DetailMovieAction, DetailMovieEvent, DetailMovieEffect, DetailMovieViewState>() {
    override fun initialState(): DetailMovieViewState = DetailMovieViewState()

    override fun handleOnAction(action: DetailMovieAction) {
        viewModelScope.launch {
            when (action) {
                is DetailMovieAction.OnRequestDetail ->  {
                    sendEvent(DetailMovieEvent.ShowLoading)
                    requestMovieDetail(action.id)
                    requestCast(action.id)
                    requestTrailers(action.id)
                    sendEvent(DetailMovieEvent.DismissLoading)
                }
                is DetailMovieAction.OnClickWatchNow -> addToWatchNow(action.id)
            }
        }
    }

    private suspend fun requestCast(id: Int) {
        when (val result = getCastUseCase.execute(GetCastUseCaseParam(id))) {
            is GetCastUseCaseResult.GetCastSuccess -> {
                sendEvent(DetailMovieEvent.ShowCasts(result.castList.take(5)))
            }

            is GetCastUseCaseResult.GetCastFailure -> {
                sendEvent(DetailMovieEvent.ShowError(result.errorMessage))
            }

        }
    }

    private suspend fun requestTrailers(id: Int) {
        when (val result = getVideoTrailersUseCase.execute(GetVideoTrailersUseCaseParam(id))) {
            is GetVideoTrailersUseCaseResult.Success -> {
                sendEvent(DetailMovieEvent.ShowTrailers(result.trailerList.take(5)))
            }

            is GetVideoTrailersUseCaseResult.Error -> {
                sendEvent(DetailMovieEvent.ShowError(result.errorMessage))
            }
        }
    }

    private suspend fun addToWatchNow(id: Int) {
        sendEffect(DetailMovieEffect.ShowToast("Added to watchlist"))
    }

    private suspend fun requestMovieDetail(id: Int) {
        when (val result = getDetailMovieUseCase.execute(GetMovieDetailUseCaseParam(id))) {
            is GetMovieDetailUseCaseResult.Success -> {
                sendEvent(DetailMovieEvent.ShowMovieDetail(result.movieDetail))
            }

            is GetMovieDetailUseCaseResult.Error -> {
                sendEvent(DetailMovieEvent.ShowError(result.message))
            }
        }
    }

    override fun reduce(
        oldState: DetailMovieViewState, event: DetailMovieEvent
    ): DetailMovieViewState {
        return DetailMovieViewState(
            imgUrl = imgUrlReducer(oldState, event),
            yearRelease = yearReducer(oldState, event),
            duration = durationReducer(oldState, event),
            listGenres = genresReducer(oldState, event),
            countReviews = reviewCountReducer(oldState, event),
            posterPath = posterUrlReducer(oldState, event),
            title = titleReducer(oldState, event),
            overview = overviewReducer(oldState, event),
            rating = ratingReducer(oldState, event),
            listCast = castReducer(oldState, event),
            listTrailers = trailersReducer(oldState, event),
        )
    }

    private fun trailersReducer(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): List<VideoTrailerUi> {
        return when (event) {
            is DetailMovieEvent.ShowTrailers -> {
                event.trailers
            }

            else -> oldState.listTrailers
        }
    }

    private fun castReducer(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): List<CastUi> {
        return when (event) {
            is DetailMovieEvent.ShowCasts -> {
                event.casts
            }

            else -> oldState.listCast
        }
    }

    private fun ratingReducer(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): Double {
        return when (event) {
            is DetailMovieEvent.ShowMovieDetail -> {
                event.movieDetail.voteAverage
            }

            else -> oldState.rating
        }
    }

    private fun reviewCountReducer(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): Int {
        return when (event) {
            is DetailMovieEvent.ShowMovieDetail -> {
                event.movieDetail.voteCount
            }

            else -> oldState.countReviews
        }
    }

    private fun genresReducer(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): List<String> {
        return when (event) {
            is DetailMovieEvent.ShowMovieDetail -> {
                event.movieDetail.genres.map { it.name }.take(2)
            }

            else -> oldState.listGenres
        }
    }


    private fun durationReducer(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): String {
        return when (event) {
            is DetailMovieEvent.ShowMovieDetail -> {
                val hours = event.movieDetail.runtime / 60
                val minutes = event.movieDetail.runtime % 60
                "${hours}h ${minutes}m"
            }

            else -> oldState.duration
        }
    }

    private fun yearReducer(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): String {
        return when (event) {
            is DetailMovieEvent.ShowMovieDetail -> {
                event.movieDetail.releaseDate.take(4)
            }

            else -> oldState.yearRelease
        }
    }

    private fun posterUrlReducer(
        oldState: DetailMovieViewState, event: DetailMovieEvent
    ): String {
        return when (event) {
            is DetailMovieEvent.ShowMovieDetail -> {
                event.movieDetail.posterPath
            }

            else -> oldState.posterPath
        }
    }

    private fun overviewReducer(
        oldState: DetailMovieViewState, event: DetailMovieEvent
    ): String {
        return when (event) {
            is DetailMovieEvent.ShowMovieDetail -> {
                event.movieDetail.overview
            }

            else -> oldState.overview
        }
    }

    private fun titleReducer(
        oldState: DetailMovieViewState, event: DetailMovieEvent
    ): String {
        return (when (event) {
            is DetailMovieEvent.ShowMovieDetail -> {
                event.movieDetail.title
            }

            else -> oldState.title
        })
    }

    private fun imgUrlReducer(
        oldState: DetailMovieViewState, event: DetailMovieEvent
    ): String {
        return when (event) {
            is DetailMovieEvent.ShowMovieDetail -> {
                event.movieDetail.backdropPath
            }

            else -> oldState.imgUrl
        }
    }

}
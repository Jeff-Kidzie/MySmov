package dev.me.mysmov.feature.detail

import androidx.lifecycle.viewModelScope
import dev.me.mysmov.core.base.BaseViewModel
import dev.me.mysmov.domain.GetMovieDetailUseCase
import dev.me.mysmov.domain.GetMovieDetailUseCaseParam
import dev.me.mysmov.domain.GetMovieDetailUseCaseResult
import kotlinx.coroutines.launch

class MovieDetailViewModel(val getDetailMovieUseCase: GetMovieDetailUseCase) :
    BaseViewModel<DetailMovieAction, DetailMovieEvent, DetailMovieEffect, DetailMovieViewState>() {
    override fun initialState(): DetailMovieViewState = DetailMovieViewState()

    override fun handleOnAction(action: DetailMovieAction) {
        viewModelScope.launch {
            when (action) {
                is DetailMovieAction.OnRequestDetail -> requestMovieDetail(action.id)
                is DetailMovieAction.OnClickWatchNow -> addToWatchNow(action.id)
            }
        }
    }

    private suspend fun addToWatchNow(id: Int) {
        sendEffect(DetailMovieEffect.ShowToast("Added to watchlist"))
    }

    private suspend fun requestMovieDetail(id: Int) {
        sendEvent(DetailMovieEvent.ShowLoading)
        when (val result = getDetailMovieUseCase.execute(GetMovieDetailUseCaseParam(id))) {
            is GetMovieDetailUseCaseResult.Success -> {
                sendEvent(DetailMovieEvent.ShowMovieDetail(result.movieDetail))
            }

            is GetMovieDetailUseCaseResult.Error -> {
                sendEvent(DetailMovieEvent.ShowError(result.message))
            }
        }
        sendEvent(DetailMovieEvent.DismissLoading)
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
            rating = ratingReducer(oldState, event)
        )
    }

    private fun ratingReducer(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): Double {
        return when(event) {
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
        return when(event) {
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
    return when(event) {
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
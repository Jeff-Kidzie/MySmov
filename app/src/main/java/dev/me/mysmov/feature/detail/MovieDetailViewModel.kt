package dev.me.mysmov.feature.detail

import androidx.lifecycle.viewModelScope
import dev.me.mysmov.core.base.BaseViewModel
import dev.me.mysmov.domain.GetMovieDetailUseCase
import dev.me.mysmov.domain.GetMovieDetailUseCaseParam
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
        val result = getDetailMovieUseCase.execute(GetMovieDetailUseCaseParam(id))
        sendEvent(DetailMovieEvent.DismissLoading)
    }

    override fun reduce(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): DetailMovieViewState {
        return DetailMovieViewState(
            imgUrl = "",
            title = "",
            overview = "",
            rating = 0.0,
        )
    }

}
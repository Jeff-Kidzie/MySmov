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
        TODO("Not yet implemented")
    }

    private suspend fun requestMovieDetail(id: Int) {
        val result = getDetailMovieUseCase.execute(GetMovieDetailUseCaseParam(id))
    }


    override fun reduce(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): DetailMovieViewState {
        TODO("Not yet implemented")
    }

}
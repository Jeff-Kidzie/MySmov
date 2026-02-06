package dev.me.mysmov.feature.detail

import androidx.lifecycle.viewModelScope
import dev.me.mysmov.core.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel : BaseViewModel<DetailMovieAction, DetailMovieEvent, DetailMovieEffect, DetailMovieViewState>() {
    override fun initialState(): DetailMovieViewState = DetailMovieViewState()

    override fun handleOnAction(action: DetailMovieAction) {
        viewModelScope.launch {
            when(action){
                DetailMovieAction.InitPage -> requestMovieDetail()
                is DetailMovieAction.OnClickWatchNow -> addToWatchNow(action.id)
            }

        }
    }

    private fun addToWatchNow(id: Int) {
        TODO("Not yet implemented")
    }

    private fun requestMovieDetail() {
        TODO("Not yet implemented")
    }


    override fun reduce(
        oldState: DetailMovieViewState,
        event: DetailMovieEvent
    ): DetailMovieViewState {
        TODO("Not yet implemented")
    }

}
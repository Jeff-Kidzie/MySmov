package dev.me.mysmov.feature.home

import dev.me.mysmov.core.BaseViewModel

class HomeViewModel : BaseViewModel<HomeAction, HomeEvent, HomeEffect, HomeViewState>() {
    override fun initialState(): HomeViewState = HomeViewState()

    override fun onAction(action: HomeAction) {
        when(action) {
            HomeAction.InitPage -> requestMovieList()
            is HomeAction.OnClickMovie -> requestMovieDetail(action.id)
            HomeAction.RefreshPage -> requestMovieList()
        }
    }

    override fun reduce(
        event: HomeEvent,
        oldState: HomeViewState
    ): HomeViewState {
        TODO("Not yet implemented")
    }

    private fun requestMovieList() {
        //TODO("request movie list")
    }

    private fun requestMovieDetail(movieId: Int) {
        //TODO("request movie detail")
    }
}

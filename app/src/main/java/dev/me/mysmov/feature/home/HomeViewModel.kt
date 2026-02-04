package dev.me.mysmov.feature.home

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.me.mysmov.core.BaseViewModel

@HiltViewModel
class HomeViewModel : BaseViewModel<HomeAction, HomeViewState>(HomeViewState()) {
    override fun onAction(action: HomeAction) {
        when(action) {
            HomeAction.InitPage -> requestMovieList()
            is HomeAction.OnClickMovie -> requestMovieDetail(action.id)
            HomeAction.RefreshPage -> requestMovieList()
        }
    }

    override fun trackViewScreen() {
        //TODO("send analytic tracker")
    }

    private fun requestMovieList() {
        //TODO("request movie list")
    }

    private fun requestMovieDetail(movieId: Int) {
        //TODO("request movie detail")
    }
}

package dev.me.mysmov.feature.home

import androidx.lifecycle.viewModelScope
import dev.me.mysmov.core.base.BaseViewModel
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.domain.MovieUseCase
import dev.me.mysmov.domain.MovieUseCaseParam
import dev.me.mysmov.domain.MovieUseCaseResult
import kotlinx.coroutines.launch

class HomeViewModel(private val discoverMovieUseCase: MovieUseCase) :
    BaseViewModel<HomeAction, HomeEvent, HomeEffect, HomeViewState>() {
    override fun initialState(): HomeViewState = HomeViewState()

    override fun handleOnAction(action: HomeAction) {
        when (action) {
            HomeAction.InitPage, HomeAction.RefreshPage -> viewModelScope.launch { requestMovieList() }
            is HomeAction.OnClickMovie -> requestMovieDetail(action.id)
        }
    }

    override fun reduce(
        oldState: HomeViewState,
        event: HomeEvent,
    ): HomeViewState {
        return HomeViewState(
            title = "",
            movies = moviesReducer(oldState, event),
            isLoading = loadingReducer(oldState,event),
            errorMessage = ""
        )
    }

    private fun loadingReducer(oldState: HomeViewState, event: HomeEvent) : Boolean {
        return when(event) {
            HomeEvent.ShowLoading -> true
            HomeEvent.DismissLoading -> false
            else -> oldState.isLoading
        }
    }

    private fun moviesReducer(oldState: HomeViewState, event: HomeEvent) : List<Movie> = when(event) {
        is HomeEvent.ShowMovies -> event.movies
        else -> oldState.movies
    }

    private suspend fun requestMovieList() {
        sendEvent(HomeEvent.ShowLoading)
        val result = discoverMovieUseCase.execute(MovieUseCaseParam)
        sendEvent(HomeEvent.DismissLoading)
        when(result) {
            is MovieUseCaseResult.Error -> HomeEvent.ShowError(result.message)
            is MovieUseCaseResult.Success -> HomeEvent.ShowMovies(result.movies)
        }
    }

    private fun requestMovieDetail(movieId: Int) {
        //TODO("request movie detail")
    }
}

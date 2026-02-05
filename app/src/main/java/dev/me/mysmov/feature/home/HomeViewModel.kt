package dev.me.mysmov.feature.home

import androidx.lifecycle.viewModelScope
import dev.me.mysmov.core.base.BaseViewModel
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.domain.MovieUseCase
import dev.me.mysmov.domain.MovieUseCaseParam
import dev.me.mysmov.domain.MovieUseCaseResult
import dev.me.mysmov.domain.NowPlayingMovieUseCase
import dev.me.mysmov.domain.NowPlayingMovieUseCaseParam
import dev.me.mysmov.domain.NowPlayingMovieUseCaseResult
import kotlinx.coroutines.launch

class HomeViewModel(
    private val discoverMovieUseCase: MovieUseCase,
    private val nowPlayingMovieUseCase: NowPlayingMovieUseCase
) :
    BaseViewModel<HomeAction, HomeEvent, HomeEffect, HomeViewState>() {
    override fun initialState(): HomeViewState = HomeViewState()

    override fun handleOnAction(action: HomeAction) {
        when (action) {
            HomeAction.InitPage, HomeAction.RefreshPage -> viewModelScope.launch {
                requestMovieList()
                requestNowPlayingMovieList()
            }

            is HomeAction.OnClickMovie -> requestMovieDetail(action.id)
        }
    }

    override fun reduce(
        oldState: HomeViewState,
        event: HomeEvent,
    ): HomeViewState {
        return HomeViewState(
            movies = moviesReducer(oldState, event),
            nowPlayingMovies = nowPlayingReducer(oldState, event),
            isLoading = loadingReducer(oldState, event),
            errorMessage = errorMessageReducer(oldState, event)
        )
    }

    private fun nowPlayingReducer(
        oldState: HomeViewState,
        event: HomeEvent
    ): List<Movie> {
        return when (event) {
            is HomeEvent.ShowNowPlayingMovies-> event.movies
            else -> oldState.nowPlayingMovies
        }
    }

    private fun errorMessageReducer(
        oldState: HomeViewState,
        event: HomeEvent
    ): String {
        return when (event) {
            is HomeEvent.ShowError -> event.message
            else -> oldState.errorMessage
        }
    }

    private fun loadingReducer(oldState: HomeViewState, event: HomeEvent): Boolean {
        return when (event) {
            HomeEvent.ShowLoading -> true
            HomeEvent.DismissLoading -> false
            else -> oldState.isLoading
        }
    }

    private fun moviesReducer(oldState: HomeViewState, event: HomeEvent): List<Movie> =
        when (event) {
            is HomeEvent.ShowMovies -> event.movies
            else -> oldState.movies
        }

    private suspend fun requestMovieList() {
        sendEvent(HomeEvent.ShowLoading)
        val result = discoverMovieUseCase.execute(MovieUseCaseParam)
        sendEvent(HomeEvent.DismissLoading)
        when (result) {
            is MovieUseCaseResult.Error -> HomeEvent.ShowError(result.message)
            is MovieUseCaseResult.Success -> HomeEvent.ShowMovies(result.movies)
        }
    }

    private suspend fun requestNowPlayingMovieList() {
        sendEvent(HomeEvent.ShowLoading)
        val result = nowPlayingMovieUseCase.execute(NowPlayingMovieUseCaseParam)
        sendEvent(HomeEvent.DismissLoading)
        when (result) {
            is NowPlayingMovieUseCaseResult.Error -> sendEvent(HomeEvent.ShowError(result.message))
            is NowPlayingMovieUseCaseResult.Success -> sendEvent(HomeEvent.ShowNowPlayingMovies(result.movies))
        }
    }

    private fun requestMovieDetail(movieId: Int) {
        //TODO("request movie detail")
    }
}

package dev.me.mysmov.feature.home

import androidx.lifecycle.viewModelScope
import dev.me.mysmov.core.base.BaseViewModel
import dev.me.mysmov.data.model.MediaItem
import dev.me.mysmov.data.model.MovieCategory
import dev.me.mysmov.domain.GetMovieByCategoryUseCase
import dev.me.mysmov.domain.GetMovieByCategoryUseCaseParam
import dev.me.mysmov.domain.GetMovieByCategoryUseCaseResult
import dev.me.mysmov.domain.movies.NowPlayingMovieUseCase
import dev.me.mysmov.domain.movies.NowPlayingMovieUseCaseParam
import dev.me.mysmov.domain.movies.NowPlayingMovieUseCaseResult
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getMovieByCategoryUseCase: GetMovieByCategoryUseCase,
    private val nowPlayingMovieUseCase: NowPlayingMovieUseCase
) : BaseViewModel<HomeAction, HomeEvent, HomeEffect, HomeViewState>() {
    override fun initialState(): HomeViewState = HomeViewState()

    override fun handleOnAction(action: HomeAction) {
        viewModelScope.launch {
            when (action) {
                HomeAction.InitPage, HomeAction.RefreshPage -> {
                    //TODO("request popular, top rated, upcoming movies")
                    requestPopularMovies()
                    requestTopRatedMovies()
                    requestUpcomingMovies()
                    requestNowPlayingMovieList()
                }

                is HomeAction.OnClickMovie -> sendEffect(
                    HomeEffect.ShowDetailMovie(
                        action.id
                    )
                )
            }
        }

    }

    private suspend fun requestTopRatedMovies() {
        val result = getMovieByCategoryUseCase.execute(
            GetMovieByCategoryUseCaseParam(MovieCategory.TopRated)
        )
        when (result) {
            is GetMovieByCategoryUseCaseResult.Error -> sendEvent(
                HomeEvent.ShowError(result.message)
            )
            is GetMovieByCategoryUseCaseResult.Success -> sendEvent(
                HomeEvent.ShowTopRatedMovies(result.mediaItems)
            )
        }
    }

    private suspend fun requestPopularMovies() {
        sendEvent(HomeEvent.ShowLoading)
        val result = getMovieByCategoryUseCase.execute(
            GetMovieByCategoryUseCaseParam(MovieCategory.Popular)
        )
        when (result) {
            is GetMovieByCategoryUseCaseResult.Error -> sendEvent(
                HomeEvent.ShowError(
                    result.message
                )
            )

            is GetMovieByCategoryUseCaseResult.Success -> sendEvent(
                HomeEvent.ShowPopularMovies(
                    result.mediaItems
                )
            )
        }
        sendEvent(HomeEvent.DismissLoading)
    }

    private suspend fun requestUpcomingMovies() {
        val result = getMovieByCategoryUseCase.execute(
            GetMovieByCategoryUseCaseParam(MovieCategory.Upcoming)
        )
        when (result) {
            is GetMovieByCategoryUseCaseResult.Error -> sendEvent(
                HomeEvent.ShowError(result.message)
            )
            is GetMovieByCategoryUseCaseResult.Success -> sendEvent(
                HomeEvent.ShowUpcomingMovies(result.mediaItems)
            )
        }
    }

    override fun reduce(
        oldState: HomeViewState,
        event: HomeEvent,
    ): HomeViewState {
        return HomeViewState(
            popularMovies = popularMoviesReducer(oldState, event),
            upcomingMovies = upcomingMoviesReducer(oldState, event),
            topRatedMovies = topRatedMoviesReducer(oldState, event),
            nowPlayingMediaItems = nowPlayingReducer(oldState, event),
            isLoading = loadingReducer(oldState, event),
            errorMessage = errorMessageReducer(oldState, event)
        )
    }

    private fun topRatedMoviesReducer(
        oldState: HomeViewState, event: HomeEvent
    ): List<MediaItem> {
        return when (event) {
            is HomeEvent.ShowTopRatedMovies -> event.mediaItems
            else -> oldState.topRatedMovies
        }
    }

    private fun upcomingMoviesReducer(
        oldState: HomeViewState, event: HomeEvent
    ): List<MediaItem> {
        return when (event) {
            is HomeEvent.ShowUpcomingMovies -> event.mediaItems
            else -> oldState.upcomingMovies
        }
    }

    private fun popularMoviesReducer(
        oldState: HomeViewState, event: HomeEvent
    ): List<MediaItem> {
        return when (event) {
            is HomeEvent.ShowPopularMovies -> event.mediaItems
            else -> oldState.popularMovies
        }
    }

    private fun nowPlayingReducer(
        oldState: HomeViewState, event: HomeEvent
    ): List<MediaItem> {
        return when (event) {
            is HomeEvent.ShowNowPlayingMovies -> event.mediaItems
            else -> oldState.nowPlayingMediaItems
        }
    }

    private fun errorMessageReducer(
        oldState: HomeViewState, event: HomeEvent
    ): String {
        return when (event) {
            is HomeEvent.ShowError -> event.message
            else -> oldState.errorMessage
        }
    }

    private fun loadingReducer(
        oldState: HomeViewState,
        event: HomeEvent
    ): Boolean {
        return when (event) {
            HomeEvent.ShowLoading -> true
            HomeEvent.DismissLoading -> false
            else -> oldState.isLoading
        }
    }

    private suspend fun requestNowPlayingMovieList() {
        sendEvent(HomeEvent.ShowLoading)
        val result = nowPlayingMovieUseCase.execute(NowPlayingMovieUseCaseParam)
        sendEvent(HomeEvent.DismissLoading)
        when (result) {
            is NowPlayingMovieUseCaseResult.Error -> sendEvent(
                HomeEvent.ShowError(
                    result.message
                )
            )

            is NowPlayingMovieUseCaseResult.Success -> sendEvent(
                HomeEvent.ShowNowPlayingMovies(
                    result.mediaItems
                )
            )
        }
    }

    private fun requestMovieDetail(movieId: Int) {
        //TODO("request movie detail")
    }
}

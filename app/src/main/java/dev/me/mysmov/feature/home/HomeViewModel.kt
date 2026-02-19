package dev.me.mysmov.feature.home

import androidx.lifecycle.viewModelScope
import dev.me.mysmov.core.base.BaseViewModel
import dev.me.mysmov.domain.model.MediaItem
import dev.me.mysmov.domain.model.MovieCategory
import dev.me.mysmov.domain.usecase.GetMovieByCategoryUseCase
import dev.me.mysmov.domain.usecase.GetMovieByCategoryUseCaseParam
import dev.me.mysmov.domain.usecase.GetMovieByCategoryUseCaseResult
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
        sendEvent(HomeEvent.ShowLoadingTopRated)
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
        sendEvent(HomeEvent.DismissLoadingTopRated)
    }

    private suspend fun requestPopularMovies() {
        sendEvent(HomeEvent.ShowLoadingPopular)
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
        sendEvent(HomeEvent.DismissLoadingPopular)
    }

    private suspend fun requestUpcomingMovies() {
        sendEvent(HomeEvent.ShowLoadingUpcoming)
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
        sendEvent(HomeEvent.DismissLoadingUpcoming)
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
            isLoadingPopular = loadingPopularReducer(oldState, event),
            isLoadingTopRated = loadingTopRatedReducer(oldState, event),
            isLoadingUpcoming = loadingUpcomingReducer(oldState, event),
            isLoadingNowPlaying = loadingNowPlayingReducer(oldState, event),
            errorMessage = errorMessageReducer(oldState, event)
        )
    }

    private fun loadingPopularReducer(
        oldState: HomeViewState,
        event: HomeEvent
    ): Boolean {
        return when (event) {
            HomeEvent.ShowLoadingPopular -> true
            HomeEvent.DismissLoadingPopular -> false
            else -> oldState.isLoadingPopular
        }
    }

    private fun loadingTopRatedReducer(
        oldState: HomeViewState,
        event: HomeEvent
    ): Boolean {
        return when (event) {
            HomeEvent.ShowLoadingTopRated -> true
            HomeEvent.DismissLoadingTopRated -> false
            else -> oldState.isLoadingTopRated
        }
    }

    private fun loadingUpcomingReducer(
        oldState: HomeViewState,
        event: HomeEvent
    ): Boolean {
        return when (event) {
            HomeEvent.ShowLoadingUpcoming -> true
            HomeEvent.DismissLoadingUpcoming -> false
            else -> oldState.isLoadingUpcoming
        }
    }

    private fun loadingNowPlayingReducer(
        oldState: HomeViewState,
        event: HomeEvent
    ): Boolean {
        return when (event) {
            HomeEvent.ShowLoadingNowPlaying -> true
            HomeEvent.DismissLoadingNowPlaying -> false
            else -> oldState.isLoadingNowPlaying
        }
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


    private suspend fun requestNowPlayingMovieList() {
        sendEvent(HomeEvent.ShowLoadingNowPlaying)
        val result = nowPlayingMovieUseCase.execute(NowPlayingMovieUseCaseParam)
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
        sendEvent(HomeEvent.DismissLoadingNowPlaying)
    }

}

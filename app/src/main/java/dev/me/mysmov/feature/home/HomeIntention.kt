package dev.me.mysmov.feature.home

import dev.me.mysmov.core.base.Action
import dev.me.mysmov.core.base.Effect
import dev.me.mysmov.core.base.Event
import dev.me.mysmov.core.base.ViewState
import dev.me.mysmov.data.model.MediaItem

sealed class HomeAction : Action {
    object InitPage : HomeAction()
    object RefreshPage : HomeAction()
    data class OnClickMovie(val id : Int) : HomeAction()
}

data class HomeViewState(
    val isLoading : Boolean = false,
    val popularMovies : List<MediaItem> = emptyList(),
    val nowPlayingMediaItems : List<MediaItem> = emptyList(),
    val topRatedMovies : List<MediaItem> = emptyList(),
    val upcomingMovies : List<MediaItem> = emptyList(),
    val errorMessage : String = "",
) : ViewState

sealed class HomeEvent : Event {
    object ShowLoading : HomeEvent()
    object DismissLoading : HomeEvent()
    data class ShowPopularMovies(val mediaItems : List<MediaItem>) : HomeEvent()
    data class ShowTopRatedMovies(val mediaItems : List<MediaItem>) : HomeEvent()
    data class ShowUpcomingMovies(val mediaItems : List<MediaItem>) : HomeEvent()
    data class ShowNowPlayingMovies(val mediaItems : List<MediaItem>) : HomeEvent()
    data class ShowError(val message : String) : HomeEvent()
}

sealed class HomeEffect : Effect {
    data class ShowDetailMovie(val id : Int) : HomeEffect()
}

package dev.me.mysmov.feature.home

import dev.me.mysmov.core.base.Action
import dev.me.mysmov.core.base.Effect
import dev.me.mysmov.core.base.Event
import dev.me.mysmov.core.base.ViewState
import dev.me.mysmov.data.model.Movie

sealed class HomeAction : Action {
    object InitPage : HomeAction()
    object RefreshPage : HomeAction()
    data class OnClickMovie(val id : Int) : HomeAction()
}

data class HomeViewState(
    val title : String = "Home",
    val isLoading : Boolean = false,
    val movies : List<Movie> = emptyList(),
    val errorMessage : String = ""
) : ViewState

sealed class HomeEvent : Event {
    object ShowLoading : HomeEvent()
    object DismissLoading : HomeEvent()
    data class ShowMovies(val movies : List<Movie>) : HomeEvent()
    data class ShowError(val message : String) : HomeEvent()
}

sealed class HomeEffect : Effect {
    data class ShowDetailMovie(val id : Int) : HomeEffect()
}

package dev.me.mysmov.feature.detail

import dev.me.mysmov.core.base.Action
import dev.me.mysmov.core.base.Effect
import dev.me.mysmov.core.base.Event
import dev.me.mysmov.core.base.ViewState
import dev.me.mysmov.data.model.Cast
import dev.me.mysmov.data.model.Movie


data class DetailMovieViewState(
    val imgUrl : String = "",
    val overview : String = "",
    val title : String = "",
    val rating : Double = 0.0,
) : ViewState

sealed class DetailMovieEffect : Effect {
    data class ShowToast(val message : String) : DetailMovieEffect()
    object NavigateToWatchList : DetailMovieEffect()
}

sealed class DetailMovieAction : Action {
    data class OnRequestDetail(val id : Int) : DetailMovieAction()
    data class OnClickWatchNow(val id : Int) : DetailMovieAction()
}

sealed class DetailMovieEvent : Event{
    data class ShowMovieDetail(val id : Int) : DetailMovieEvent()
    object ShowLoading : DetailMovieEvent()
    object DismissLoading : DetailMovieEvent()
    data class ShowError(val message : String) : DetailMovieEvent()
    data class ShowCasts(val casts : List<Cast>) : DetailMovieEvent()
    data class ShowRelatedMovies(val movies : List<Movie>) : DetailMovieEvent()
}
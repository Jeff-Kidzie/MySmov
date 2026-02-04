package dev.me.mysmov.feature.home

import dev.me.mysmov.core.Action
import dev.me.mysmov.core.ViewState

sealed class HomeAction : Action {
    object InitPage : HomeAction()
    object RefreshPage : HomeAction()
    data class OnClickMovie(val id : Int) : HomeAction()
}

data class HomeViewState(
    val title : String = "Home"
) : ViewState

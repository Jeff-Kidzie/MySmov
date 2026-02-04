package dev.me.mysmov.core

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<A : Action, VS : ViewState>(initialState: VS) : ViewModel() {
    abstract fun onAction(action: A)
    abstract fun trackViewScreen()
    private val _viewState = MutableStateFlow(initialState)
    val viewState : StateFlow<VS> = _viewState.asStateFlow()

    init {
        trackViewScreen()
    }


}

interface Action
interface ViewState
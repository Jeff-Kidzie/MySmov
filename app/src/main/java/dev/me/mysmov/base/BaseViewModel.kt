package dev.me.mysmov.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<A : Action, EV : Event, EF : Effect, VS : ViewState> : ViewModel() {

    protected abstract fun initialState() : VS
    protected abstract fun onAction(action: A)
    protected abstract fun reduce(event : EV, oldState : VS) : VS

    // view state flow
    private val _viewState = MutableStateFlow(initialState())
    val viewState : StateFlow<VS> = _viewState

    private val _effect = MutableSharedFlow<EF>()
    val effect : SharedFlow<EF> = _effect

    protected fun sendEvent(ev : EV) {
        _viewState.update { old -> reduce(ev, old)}
    }

    protected fun sendEffect(ef : EF) {
        viewModelScope.launch {
            _effect.emit(ef)
        }
    }



}

interface Action
interface ViewState
interface Effect
interface Event
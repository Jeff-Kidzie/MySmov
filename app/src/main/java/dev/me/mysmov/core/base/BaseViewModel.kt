package dev.me.mysmov.core.base

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
    protected abstract fun handleOnAction(action: A)
    protected abstract fun reduce(oldState : VS, event : EV) : VS

    // view state flow
    private val _viewState = MutableStateFlow(initialState())
    val viewState : StateFlow<VS> = _viewState

    private val _effect = MutableSharedFlow<EF>()
    val effect : SharedFlow<EF> = _effect

    fun onAction(action: A) {
        handleOnAction(action)
    }

    protected fun sendEvent(ev : EV) {
        _viewState.update { old -> reduce(old, ev)}
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
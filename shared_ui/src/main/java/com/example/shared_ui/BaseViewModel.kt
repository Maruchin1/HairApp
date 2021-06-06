package com.example.shared_ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<State>(defaultState: State) : ViewModel() {

    private val _state = MutableStateFlow(defaultState)

    val state: StateFlow<State> = _state

    protected suspend fun reduce(reducer: (State) -> State) {
        _state.emit(reducer(state.value))
    }
}
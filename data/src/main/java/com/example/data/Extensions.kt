package com.example.data

import androidx.lifecycle.MutableLiveData

internal inline fun <T> MutableLiveData<T>.updateState(update: (T) -> Unit) {
    val state = value ?: return
    update(state)
    value = state
}
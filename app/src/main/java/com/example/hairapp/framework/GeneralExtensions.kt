package com.example.hairapp.framework

import androidx.lifecycle.MutableLiveData

inline fun <T> MutableLiveData<T>.updateState(update: (T) -> Unit) {
    val state = value ?: return
    update(state)
    value = state
}

fun <T> resultFailure(message: String): Result<T> {
    val exception = IllegalStateException(message)
    return Result.failure(exception)
}

package com.example.data.room

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal inline fun <I, O> Flow<List<I>>.mapList(crossinline transform: suspend (itemValue: I) -> O): Flow<List<O>> {
    return this.map { list ->
        list.map { transform.invoke(it) }
    }
}
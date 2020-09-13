package com.example.core.base

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in I : Any, out O : Any> {

    operator fun invoke(input: I): Flow<O> {
        return execute(input)
    }

    protected abstract fun execute(input: I): Flow<O>
}
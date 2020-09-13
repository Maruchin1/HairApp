package com.example.core.base

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in I : Any, out O : Any> {

    abstract operator fun invoke(input: I): Flow<O>
}
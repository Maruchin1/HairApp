package com.example.hairapp.use_case

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in I : Any, out O : Any> {

    abstract fun execute(input: I): Flow<O>
}
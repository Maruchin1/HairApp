package com.example.core

import com.example.core.base.FlowUseCase
import com.example.core.base.UseCase
import com.example.core.base.UseCaseResult
import kotlinx.coroutines.flow.Flow

suspend operator fun <O : Any> UseCase<Unit, O>.invoke(): UseCaseResult<O> {
    return invoke(Unit)
}

operator fun <O : Any> FlowUseCase<Unit, O>.invoke(): Flow<O> {
    return invoke(Unit)
}
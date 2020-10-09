package com.example.core.base

import kotlinx.coroutines.flow.Flow

suspend operator fun <O : Any> UseCase<Unit, O>.invoke(): Result<O> {
    return invoke(Unit)
}

operator fun <O : Any> FlowUseCase<Unit, O>.invoke(): Flow<O> {
    return invoke(Unit)
}
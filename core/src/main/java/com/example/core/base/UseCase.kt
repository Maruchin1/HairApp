package com.example.core.base

abstract class UseCase<in I : Any, out O : Any> {

    suspend operator fun invoke(input: I): UseCaseResult<O> = try {
        val result = execute(input)
        UseCaseResult.Success(result)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }

    protected abstract suspend fun execute(input: I): O
}
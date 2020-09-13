package com.example.core.base

abstract class UseCase<in I : Any, out O : Any> {

    suspend operator fun invoke(input: I): Result<O> = try {
        val result = execute(input)
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    protected abstract suspend fun execute(input: I): O
}
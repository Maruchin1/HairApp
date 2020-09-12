package com.example.hairapp.use_case

abstract class UseCase<in I : Any, out O : Any> {

    suspend fun execute(input: I): UseCaseResult<O> = try {
        val result = doWork(input)
        UseCaseResult.Success(result)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }

    abstract suspend fun doWork(input: I): O
}
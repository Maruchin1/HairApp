package com.example.core.base

sealed class UseCaseResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : UseCaseResult<T>()

    data class Error(val exception: Exception) : UseCaseResult<Nothing>()
}
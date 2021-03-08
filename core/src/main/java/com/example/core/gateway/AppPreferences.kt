package com.example.core.gateway

import com.example.core.domain.CaresForBalance
import kotlinx.coroutines.flow.Flow

interface AppPreferences {

    fun getCaresForBalance(): Flow<CaresForBalance>

    suspend fun setCaresForBalance(value: CaresForBalance)
}
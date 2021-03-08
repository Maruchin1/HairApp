package com.example.core.gateway

import com.example.core.domain.CaresLimit
import kotlinx.coroutines.flow.Flow

interface AppPreferences {

    fun getPehBalanceCaresLimit(): Flow<CaresLimit>

    fun getProductsRankingCaresLimit(): Flow<CaresLimit>

    suspend fun setPehBalanceCaresLimit(value: CaresLimit)

    suspend fun setProductsRankingCaresLimit(value: CaresLimit)
}
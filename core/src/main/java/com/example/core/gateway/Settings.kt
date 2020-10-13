package com.example.core.gateway

import com.example.core.domain.CareStep
import kotlinx.coroutines.flow.Flow

interface Settings {

    suspend fun setCareSchema(schema: List<CareStep>)

    fun getCareSchema(): Flow<List<CareStep>>
}
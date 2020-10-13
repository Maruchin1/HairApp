package com.example.core.gateway

import com.example.core.domain.CareSchema
import kotlinx.coroutines.flow.Flow

interface CareSchemaRepo {

    suspend fun addNew(careSchema: CareSchema): Int

    suspend fun update(careSchema: CareSchema)

    suspend fun delete(careSchema: CareSchema)

    fun findById(id: Int): Flow<CareSchema>

    fun findAll(): Flow<List<CareSchema>>
}
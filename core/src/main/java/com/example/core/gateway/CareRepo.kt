package com.example.core.gateway

import com.example.core.domain.Care
import kotlinx.coroutines.flow.Flow

interface CareRepo {

    suspend fun add(care: Care)

    suspend fun update(care: Care)

    suspend fun delete(care: Care)

    suspend fun deleteCarePhoto(data: String)

    fun findAll(): Flow<List<Care>>

    fun findById(careId: Int): Flow<Care>

    fun findLastN(numOfCares: Int): Flow<List<Care>>
}
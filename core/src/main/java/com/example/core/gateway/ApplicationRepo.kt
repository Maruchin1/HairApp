package com.example.core.gateway

import com.example.core.domain.Application
import kotlinx.coroutines.flow.Flow

interface ApplicationRepo {

    fun findAll(): Flow<List<Application>>
}
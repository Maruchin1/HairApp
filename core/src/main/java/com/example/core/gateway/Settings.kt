package com.example.core.gateway

import kotlinx.coroutines.flow.Flow

interface Settings {

    suspend fun addManufacturer(manufacturer: String)

    fun findSavedManufacturers(): Flow<List<String>>
}
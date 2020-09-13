package com.example.core.gateway

import com.example.core.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepo {

    suspend fun addNew(product: Product)

    suspend fun delete(product: Product)

    suspend fun existsByName(productName: String): Boolean

    fun findByName(productName: String): Flow<Product>

    fun findAll(): Flow<List<Product>>
}
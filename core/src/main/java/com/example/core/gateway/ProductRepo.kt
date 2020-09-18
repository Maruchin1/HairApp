package com.example.core.gateway

import com.example.core.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepo {

    suspend fun add(product: Product)

    suspend fun update(product: Product)

    suspend fun delete(product: Product)

    suspend fun existsByName(productName: String): Boolean

    fun findById(productId: Int): Flow<Product>

    fun findAll(): Flow<List<Product>>
}
package com.example.core.gateway

import com.example.core.domain.Product
import com.example.core.domain.Application
import kotlinx.coroutines.flow.Flow

interface ProductRepo {

    suspend fun add(product: Product)

    suspend fun update(product: Product)

    suspend fun delete(product: Product)

    fun findById(productId: Int): Flow<Product>

    fun findAll(): Flow<List<Product>>

    fun findByApplicationType(type: Application.Type): Flow<List<Product>>
}
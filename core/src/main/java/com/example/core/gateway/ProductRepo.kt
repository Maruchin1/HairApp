package com.example.core.gateway

import com.example.core.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepo {

    suspend fun addNewProduct(product: Product)

    suspend fun existsByName(productName: String): Boolean

    fun findAllFlow(): Flow<List<Product>>
}
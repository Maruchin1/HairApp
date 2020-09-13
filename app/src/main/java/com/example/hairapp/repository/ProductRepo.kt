package com.example.hairapp.repository

import com.example.hairapp.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepo {

    suspend fun addNewProduct(product: Product)

    suspend fun existsByName(productName: String): Boolean

    fun findAllFlow(): Flow<List<Product>>
}
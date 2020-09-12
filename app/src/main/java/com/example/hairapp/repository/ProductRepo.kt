package com.example.hairapp.repository

import com.example.hairapp.model.Product

interface ProductRepo {

    suspend fun addNewProduct(product: Product)

    suspend fun existsByName(productName: String): Boolean
}
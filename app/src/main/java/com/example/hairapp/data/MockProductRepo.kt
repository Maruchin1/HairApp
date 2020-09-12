package com.example.hairapp.data

import com.example.hairapp.model.Product
import com.example.hairapp.repository.ProductRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockProductRepo @Inject constructor() : ProductRepo {
    private val collection = mutableSetOf<Product>()

    override suspend fun addNewProduct(product: Product) {
        collection.add(product)
    }

    override suspend fun existsByName(productName: String): Boolean {
        return collection.find { it.name == productName } != null
    }

}
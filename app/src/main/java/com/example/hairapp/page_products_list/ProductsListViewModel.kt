package com.example.hairapp.page_products_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.Product
import com.example.core.gateway.ProductRepo
import kotlinx.coroutines.flow.map

class ProductsListViewModel(
    private val productRepo: ProductRepo
) : ViewModel() {

    private val allProducts = productRepo.findAll()

    val alphabeticalProducts: LiveData<List<Product>> = allProducts
        .map { sortProductsAlphabetically(it) }
        .asLiveData()

    val noProducts: LiveData<Boolean> = allProducts
        .map { it.isEmpty() }
        .asLiveData()

    private fun sortProductsAlphabetically(products: List<Product>): List<Product> {
        return products.sortedBy { it.name }
    }
}
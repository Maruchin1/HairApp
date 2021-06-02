package com.example.select_product.components

import androidx.lifecycle.*
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class SelectProductViewModel(
    private val productDao: ProductDao
) : ViewModel() {

    private val selectedProductTypeState = MutableStateFlow<Product.Type?>(null)

    private val allProductsFlow = productDao.getAll()

    private val productsOfSelectedTypeFlow = selectedProductTypeState
        .combine(allProductsFlow) { type, products -> getFilteredByOptionalType(products, type) }
        .map { getSortedAlphabetically(it) }

    val selectedProductType: LiveData<Product.Type?> = selectedProductTypeState
        .asLiveData()

    val products: LiveData<List<Product>> = productsOfSelectedTypeFlow.asLiveData()

    fun onProductTypeSelected(type: Product.Type?) = viewModelScope.launch {
        selectedProductTypeState.emit(type)
    }

    private fun getFilteredByOptionalType(
        products: List<Product>,
        type: Product.Type?
    ): List<Product> {
        return type?.let { getFilteredByType(products, it) } ?: products
    }

    private fun getFilteredByType(products: List<Product>, type: Product.Type): List<Product> {
        return products.filter { product ->
            product.applications.any { it in type.applications }
        }
    }

    private fun getSortedAlphabetically(products: List<Product>): List<Product> {
        return products.sortedBy { it.name }
    }
}
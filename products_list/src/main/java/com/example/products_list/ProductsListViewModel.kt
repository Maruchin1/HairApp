package com.example.products_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class ProductsListViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {

    private val allProductsFlow = productDao.getAll()

    val products: LiveData<List<Product>> = allProductsFlow
        .map { getSortedAlphabetically(it) }
        .asLiveData()

    val noProducts: LiveData<Boolean> = allProductsFlow
        .map { it.isEmpty() }
        .asLiveData()

    private fun getSortedAlphabetically(products: List<Product>): List<Product> {
        return products.sortedBy { it.name }
    }
}
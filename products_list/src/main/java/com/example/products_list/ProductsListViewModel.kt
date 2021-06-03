package com.example.products_list

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.navigation.ProductDetailsDestination
import com.example.navigation.ProductDetailsParams
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class ProductsListViewModel(
    private val productDao: ProductDao,
    private val productDetailsDestination: ProductDetailsDestination
) : ViewModel() {

    private val allProductsFlow = productDao.getAll()

    val products: LiveData<List<Product>> = allProductsFlow
        .map { getSortedAlphabetically(it) }
        .asLiveData()

    val noProducts: LiveData<Boolean> = allProductsFlow
        .map { it.isEmpty() }
        .asLiveData()

    fun onAddProductClick(activity: Activity) = viewModelScope.launch {
        productDetailsDestination.navigate(
            originActivity = activity,
            params = ProductDetailsParams(-1)
        )
    }

    private fun getSortedAlphabetically(products: List<Product>): List<Product> {
        return products.sortedBy { it.name }
    }
}
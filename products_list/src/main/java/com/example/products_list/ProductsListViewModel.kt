package com.example.products_list

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.corev2.navigation.ProductFormDestination
import kotlinx.coroutines.flow.map

internal class ProductsListViewModel(
    private val productDao: ProductDao,
    private val productFormDestination: ProductFormDestination
) : ViewModel() {

    private val allProductsFlow = productDao.getAll()

    val products: LiveData<List<Product>> = allProductsFlow
        .map { getSortedAlphabetically(it) }
        .asLiveData()

    val noProducts: LiveData<Boolean> = allProductsFlow
        .map { it.isEmpty() }
        .asLiveData()

    fun onAddProductClick(activity: Activity) {
        productFormDestination.navigate(
            originActivity = activity,
            params = ProductFormDestination.Params(null)
        )
    }

    private fun getSortedAlphabetically(products: List<Product>): List<Product> {
        return products.sortedBy { it.name }
    }
}
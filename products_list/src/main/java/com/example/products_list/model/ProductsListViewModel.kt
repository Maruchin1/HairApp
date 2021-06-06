package com.example.products_list.model

import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.Product
import com.example.shared_ui.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class ProductsListViewModel(
    private val productDao: ProductDao,
    private val actions: Actions,
    private val addNewProductUseCase: AddNewProductUseCase,
) : BaseViewModel<PageState>(PageState()) {

    init {
        observeAllProducts()
    }

    fun onAddProductClicked() = viewModelScope.launch {
        addNewProductUseCase()
    }

    fun onProductClicked(productId: Long) = viewModelScope.launch {
        actions.openProductDetails(productId)
    }

    private fun observeAllProducts() = viewModelScope.launch {
        productDao.getAll()
            .map { getSortedAlphabetically(it) }
            .collectLatest { updateState(it) }
    }

    private fun getSortedAlphabetically(products: List<Product>): List<Product> =
        products.sortedBy { it.name }


    private suspend fun updateState(products: List<Product>) =
        reduce { it.copy(products = products) }

    interface Actions {
        suspend fun openProductDetails(productId: Long)
    }
}
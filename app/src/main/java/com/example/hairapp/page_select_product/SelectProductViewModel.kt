package com.example.hairapp.page_select_product

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.Product
import com.example.core.domain.ProductApplication
import com.example.core.use_case.ShowProductsToSelect

class SelectProductViewModel @ViewModelInject constructor(
    private val showProductsToSelect: ShowProductsToSelect
) : ViewModel() {

    private val productApplicationType = MutableLiveData<ProductApplication.Type?>(null)

    val products: LiveData<List<Product>> = productApplicationType.switchMap {
        val input = ShowProductsToSelect.Input(it)
        showProductsToSelect(input).asLiveData()
    }

    fun selectProductApplicationType(type: ProductApplication.Type) {
        productApplicationType.value = type
    }
}
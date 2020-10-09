package com.example.hairapp.page_select_product

import androidx.lifecycle.*
import com.example.core.domain.Product
import com.example.core.domain.Application
import com.example.core.use_case.ShowProductsToSelect

class SelectProductViewModel(
    private val showProductsToSelect: ShowProductsToSelect
) : ViewModel() {

    private val productApplicationType = MutableLiveData<Application.Type?>(null)

    val title: LiveData<String> = productApplicationType.map {
        when (it) {
            Application.Type.CONDITIONER -> "Wybierz odżywkę"
            Application.Type.SHAMPOO -> "Wybierz szampon"
            else -> "Wybierz produkt"
        }
    }

    val products: LiveData<List<Product>> = productApplicationType.switchMap {
        val input = ShowProductsToSelect.Input(it)
        showProductsToSelect(input).asLiveData()
    }

    val noProducts: LiveData<Boolean> = products.map { it.isEmpty() }

    fun selectProductApplicationType(type: Application.Type?) {
        productApplicationType.value = type
    }
}
package com.example.hairapp.page_product

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.Product
import com.example.core.use_case.ShowSelectedProduct

class ProductViewModel @ViewModelInject constructor(
    private val showSelectedProduct: ShowSelectedProduct
) : ViewModel() {
    private val selectedProductName = MutableLiveData<String>()
    private val selectedProduct: LiveData<Product> = selectedProductName.switchMap {
        val input = ShowSelectedProduct.Input(productName = it)
        showSelectedProduct(input).asLiveData()
    }

    val productPhoto: LiveData<Uri?> = selectedProduct.map { product ->
        product.photoData?.let { Uri.parse(it) }
    }

    fun selectProduct(productName: String) {
        selectedProductName.value = productName
    }
}
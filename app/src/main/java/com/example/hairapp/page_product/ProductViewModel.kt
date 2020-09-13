package com.example.hairapp.page_product

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.Product
import com.example.core.use_case.DeleteProduct
import com.example.core.use_case.ShowSelectedProduct

class ProductViewModel @ViewModelInject constructor(
    private val showSelectedProduct: ShowSelectedProduct,
    private val deleteProduct: DeleteProduct
) : ViewModel() {
    private val selectedProductName = MutableLiveData<String>()
    private val selectedProduct: LiveData<Product> = selectedProductName.switchMap {
        val input = ShowSelectedProduct.Input(productName = it)
        showSelectedProduct(input).asLiveData()
    }

    val productPhoto: LiveData<Uri?> = selectedProduct.map { product ->
        product.photoData?.let { Uri.parse(it) }
    }
    val productName: LiveData<String> = selectedProduct.map { it.name }
    val productManufacturer: LiveData<String> = selectedProduct.map { it.manufacturer }
    val typeNotSpecified: LiveData<Boolean> = selectedProduct.map { it.typeNotSpecified }
    val humectants: LiveData<Boolean> = selectedProduct.map { it.type.humectants }
    val emollients: LiveData<Boolean> = selectedProduct.map { it.type.emollients }
    val proteins: LiveData<Boolean> = selectedProduct.map { it.type.proteins }
    val applicationNotSpecified: LiveData<Boolean> = selectedProduct.map { it.applicationNotSpecified }
    val application: LiveData<List<String>> = selectedProduct.map { it.application.toList() }

    fun selectProduct(productName: String) {
        selectedProductName.value = productName
    }

    suspend fun deleteProduct(): Result<Unit> {
        val input = DeleteProduct.Input(productName = selectedProductName.value!!)
        return deleteProduct(input)
    }
}
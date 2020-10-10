package com.example.hairapp.page_product

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.core.domain.Product
import com.example.core.use_case.DeleteProduct
import com.example.core.use_case.ShowSelectedProduct

class ProductViewModel(
    private val showSelectedProduct: ShowSelectedProduct,
    private val deleteProduct: DeleteProduct
) : ViewModel() {

    private val selectedProductId = MutableLiveData<Int>()
    private val selectedProduct: LiveData<Product> = selectedProductId.switchMap {
        val input = ShowSelectedProduct.Input(productId = it)
        showSelectedProduct(input).asLiveData()
    }.map {
        Log.d("MyDebug", "product changed: $it")
        it
    }

    val productPhoto: LiveData<Uri?> = selectedProduct.map { product ->
        product.photoData?.let { Uri.parse(it) }
    }
    val productName: LiveData<String> = selectedProduct.map {
        it.name
    }
    val productManufacturer: LiveData<String> = selectedProduct.map {
        it.manufacturer
    }
    val typeNotSpecified: LiveData<Boolean> = selectedProduct.map {
        it.typeNotSpecified
    }
    val humectants: LiveData<Boolean> = selectedProduct.map {
        it.composition.humectants
    }
    val emollients: LiveData<Boolean> = selectedProduct.map {
        it.composition.emollients
    }
    val proteins: LiveData<Boolean> = selectedProduct.map {
        it.composition.proteins
    }
    val applicationNotSpecified: LiveData<Boolean> = selectedProduct.map {
        it.applicationNotSpecified
    }
    val applications: LiveData<List<Product.Application>> = selectedProduct.map {
        it.applications.toList()
    }

    fun selectProduct(productId: Int) {
        selectedProductId.value = productId
    }

    fun getProductId(): Int? = selectedProductId.value

    suspend fun deleteProduct(): Result<Unit> {
        val input = DeleteProduct.Input(productId = selectedProductId.value!!)
        return deleteProduct(input)
    }
}
package com.example.hairapp.page_product_form

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.PRODUCT_APPLICATIONS
import com.example.core.domain.Product
import com.example.core.use_case.*
import com.example.hairapp.R
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

class ProductFormViewModel @ViewModelInject constructor(
    private val showSelectedProduct: ShowSelectedProduct,
    private val addProduct: AddProduct
) : ViewModel() {

    val photoPlaceholderId: LiveData<Int> = liveData {
        val id = R.drawable.ic_round_add_a_photo_24
        emit(id)
    }
    val productApplicationOptions: LiveData<List<String>> = liveData {
        emit(PRODUCT_APPLICATIONS.toList())
    }

    val productPhoto = MutableLiveData<Uri?>(null)
    val productName = MutableLiveData("")
    val productManufacturer = MutableLiveData("")
    val humectants = MutableLiveData(false)
    val emollients = MutableLiveData(false)
    val proteins = MutableLiveData(false)
    val productApplication = MutableLiveData(setOf<String>())

    fun setEditProductAsync(productName: String): Deferred<Result<Unit>> = viewModelScope.async {
        val input = ShowSelectedProduct.Input(productName)
        showSelectedProduct(input).runCatching {
            val product = this.first()
            dispatchProductToEdit(product)
        }
    }

    suspend fun saveProduct(): Result<Unit> {
        val input = makeInput()
        return addProduct(input)
    }

    private fun makeInput() = AddProduct.Input(
        productName = productName.value!!,
        productManufacturer = productManufacturer.value!!,
        emollient = emollients.value!!,
        humectant = humectants.value!!,
        protein = proteins.value!!,
        productApplications = productApplication.value!!,
        productPhotoData = productPhoto.value!!.toString()
    )

    private fun dispatchProductToEdit(product: Product) {
        productPhoto.postValue(product.photoData?.let { Uri.parse(it) })
        productName.postValue(product.name)
        productManufacturer.postValue(product.manufacturer)
        emollients.postValue(product.type.emollients)
        humectants.postValue(product.type.humectants)
        proteins.postValue(product.type.proteins)
        productApplication.postValue(product.application)
    }
}
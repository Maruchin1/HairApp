package com.example.hairapp.page_new_product

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.base.UseCaseResult
import com.example.core.domain.PRODUCT_APPLICATIONS
import com.example.core.use_case.AddProduct

class NewProductViewModel @ViewModelInject constructor(
    private val addProduct: AddProduct
) : ViewModel() {

    val productName = MutableLiveData("")

    val productManufacturer = MutableLiveData("")

    val emollient = MutableLiveData(false)

    val humectant = MutableLiveData(false)

    val protein = MutableLiveData(false)

    val productApplication = MutableLiveData(setOf<String>())

    val productPhoto = MutableLiveData<Uri?>(null)

    val productApplicationOptions: LiveData<List<String>> = liveData {
        emit(PRODUCT_APPLICATIONS.toList())
    }

    suspend fun saveProduct(): String? {
        val input = makeInput()
        val result = addProduct(input)
        return if (result is UseCaseResult.Error) {
            result.toMessage()
        } else {
            null
        }
    }

    private fun makeInput() = AddProduct.Input(
        productName = productName.value!!,
        productManufacturer = productManufacturer.value!!,
        emollient = emollient.value!!,
        humectant = humectant.value!!,
        protein = protein.value!!,
        productApplications = productApplication.value!!,
        productPhotoData = productPhoto.value!!.toString()
    )

    private fun UseCaseResult.Error.toMessage(): String = when (exception) {
        is AddProduct.ProductAlreadyExistsException -> "Produkt o podanej nazwie już istnieje"
        else -> "Nieoczekiwany błąd. Spróbuj ponownie"
    }

}
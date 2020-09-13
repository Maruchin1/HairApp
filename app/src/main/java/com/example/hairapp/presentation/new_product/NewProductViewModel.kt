package com.example.hairapp.presentation.new_product

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.hairapp.model.PRODUCT_APPLICATIONS
import com.example.hairapp.use_case.NewProductUseCase
import com.example.hairapp.use_case.UseCaseResult

class NewProductViewModel @ViewModelInject constructor(
    private val newProductUseCase: NewProductUseCase
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
        val result = newProductUseCase.execute(input)
        return if (result is UseCaseResult.Error) {
            result.toMessage()
        } else {
            null
        }
    }

    private fun makeInput() = NewProductUseCase.Input(
        productName = productName.value!!,
        productManufacturer = productManufacturer.value!!,
        emollient = emollient.value!!,
        humectant = humectant.value!!,
        protein = protein.value!!,
        productApplications = productApplication.value!!
    )

    private fun UseCaseResult.Error.toMessage(): String = when (exception) {
        is NewProductUseCase.ProductAlreadyExistsException -> "Produkt o podanej nazwie już istnieje"
        else -> "Nieoczekiwany błąd. Spróbuj ponownie"
    }

}
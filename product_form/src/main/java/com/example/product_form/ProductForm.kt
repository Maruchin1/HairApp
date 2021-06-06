package com.example.product_form

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.corev2.entities.Ingredients
import com.example.corev2.entities.Product

internal class ProductForm {
    val productPhoto = MutableLiveData<Uri?>(null)
    val productName = MutableLiveData("")
    val manufacturer = MutableLiveData("")
    val proteins = MutableLiveData(false)
    val emollients = MutableLiveData(false)
    val humectants = MutableLiveData(false)
    val productApplications = MutableLiveData(setOf<Product.Application>())

    val isValid: Boolean
        get() = !productName.value.isNullOrEmpty() &&
                !productName.value.isNullOrBlank()

    fun createProduct() = Product(
        name = productName.value!!,
        manufacturer = manufacturer.value!!,
        ingredients = Ingredients(
            proteins = proteins.value!!,
            emollients = emollients.value!!,
            humectants = humectants.value!!
        ),
        applications = productApplications.value!!,
        photoData = productPhoto.value?.toString()
    )
}
package com.example.product_form

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.ProductDao
import com.example.corev2.entities.CompositionOfIngredients
import com.example.corev2.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProductFormViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {

    val productPhoto = MutableLiveData<Uri?>(null)
    val productName = MutableLiveData("")
    val manufacturer = MutableLiveData("")
    val proteins = MutableLiveData(false)
    val emollients = MutableLiveData(false)
    val humectants = MutableLiveData(false)
    val productApplications = MutableLiveData(setOf<Product.Application>())

    suspend fun onSaveClick() {
        val newProduct = createNewProduct()
        if (newProduct.isValid()) {
            productDao.insert(newProduct)
        }
    }

    private fun createNewProduct() = Product(
        name = productName.value!!,
        manufacturer = manufacturer.value!!,
        compositionOfIngredients = CompositionOfIngredients(
            proteins = proteins.value!!,
            emollients = emollients.value!!,
            humectants = humectants.value!!
        ),
        applications = productApplications.value!!,
        photoData = productPhoto.value?.toString()
    )
}
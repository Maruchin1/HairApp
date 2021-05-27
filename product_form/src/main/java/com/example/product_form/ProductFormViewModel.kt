package com.example.product_form

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.ProductDao
import com.example.corev2.ui.ImagePickerService
import kotlinx.coroutines.launch


internal class ProductFormViewModel(
    val form: ProductForm,
    private val productDao: ProductDao,
    private val imagePickerService: ImagePickerService
) : ViewModel() {

    fun takePhoto(activity: Activity) = viewModelScope.launch {
        imagePickerService.takeProductPhoto(activity)?.let {
            form.productPhoto.postValue(it)
        }
    }

    suspend fun onSaveClick() {
        if (form.isValid) {
            val newProduct = form.createProduct()
            productDao.insert(newProduct)
        }
    }
}
package com.example.product_form

import androidx.lifecycle.ViewModel
import com.example.corev2.dao.ProductDao


internal class ProductFormViewModel(
    val form: ProductForm,
    private val productDao: ProductDao
) : ViewModel() {

    suspend fun onSaveClick() {
        if (form.isValid) {
            val newProduct = form.createProduct()
            productDao.insert(newProduct)
        }
    }
}
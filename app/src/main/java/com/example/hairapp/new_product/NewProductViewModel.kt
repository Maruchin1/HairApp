package com.example.hairapp.new_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.hairapp.model.PRODUCT_APPLICATIONS

class NewProductViewModel : ViewModel() {

    val productApplications: LiveData<List<String>> = liveData {
        emit(PRODUCT_APPLICATIONS.toList())
    }
}
package com.example.hairapp.page_home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.Product
import com.example.core.invoke
import com.example.core.use_case.ShowProductsList

class HomeViewModel @ViewModelInject constructor(
    showProductsList: ShowProductsList
) : ViewModel() {

    val products: LiveData<List<Product>> = showProductsList().asLiveData()
}
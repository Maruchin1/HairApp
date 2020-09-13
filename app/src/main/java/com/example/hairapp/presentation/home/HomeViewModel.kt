package com.example.hairapp.presentation.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.hairapp.model.Product
import com.example.hairapp.use_case.GetProductsUseCase

class HomeViewModel @ViewModelInject constructor(
    getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    val products: LiveData<List<Product>> = getProductsUseCase.execute(Unit).asLiveData()
}
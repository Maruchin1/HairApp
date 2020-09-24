package com.example.hairapp.page_home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.core.domain.Care
import com.example.core.domain.Product
import com.example.core.invoke
import com.example.core.use_case.ShowCaresList
import com.example.core.use_case.ShowProductsList

class HomeViewModel @ViewModelInject constructor(
    showProductsList: ShowProductsList,
    showCaresList: ShowCaresList
) : ViewModel() {

    val cares: LiveData<List<Care>> = showCaresList().asLiveData().map {
        Log.d("MyDebug", "$it")
        it
    }

    val products: LiveData<List<Product>> = showProductsList().asLiveData()
}
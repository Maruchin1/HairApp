package com.example.hairapp.page_home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.core.domain.Care
import com.example.core.domain.Product
import com.example.core.base.invoke
import com.example.core.domain.CareSchema
import com.example.core.use_case.ShowCareSchemas
import com.example.core.use_case.ShowCaresList
import com.example.core.use_case.ShowProductsList
import kotlinx.coroutines.flow.first

class HomeViewModel(
    showProductsList: ShowProductsList,
    showCaresList: ShowCaresList,
    private val showCareSchemas: ShowCareSchemas
) : ViewModel() {

    val cares: LiveData<List<Care>> = showCaresList().asLiveData().map {
        Log.d("MyDebug", "$it")
        it
    }

    val noCares: LiveData<Boolean> = cares.map { it.isEmpty() }

    val products: LiveData<List<Product>> = showProductsList().asLiveData()

    val noProducts: LiveData<Boolean> = products.map { it.isEmpty() }

    suspend fun getCareSchemas(): List<CareSchema> {
        return showCareSchemas().first()
    }
}
package com.example.hairapp.page_home

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.Product
import com.example.core.invoke
import com.example.core.use_case.ShowProductsList
import kotlinx.coroutines.flow.map

class HomeViewModel @ViewModelInject constructor(
    showProductsList: ShowProductsList
) : ViewModel() {

    val products: LiveData<List<ProductItem>> = showProductsList()
        .map { it.toProductItems() }
        .asLiveData()

    private fun Product.toProductItem() = ProductItem(
        productPhoto = photoData?.let { Uri.parse(it) },
        productName = name,
        productManufacturer = manufacturer
    )

    private fun List<Product>.toProductItems() = map {
        it.toProductItem()
    }
}
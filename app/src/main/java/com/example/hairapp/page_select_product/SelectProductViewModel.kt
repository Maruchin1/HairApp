package com.example.hairapp.page_select_product

import androidx.lifecycle.*
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import com.example.core.use_case.ShowProductsToSelect

class SelectProductViewModel(
    private val showProductsToSelect: ShowProductsToSelect
) : ViewModel() {

    private val careStepType = MutableLiveData<CareStep.Type?>(null)

    val title: LiveData<String> = careStepType.map {
        when (it) {
            CareStep.Type.CONDITIONER -> "Wybierz odżywkę"
            CareStep.Type.SHAMPOO -> "Wybierz szampon"
            CareStep.Type.OIL -> "Wybierz olej"
            CareStep.Type.EMULSIFIER -> "Wybierz emulgator"
            CareStep.Type.STYLIZER -> "Wybierz stylizator"
            else -> "Wybierz produkt"
        }
    }

    val products: LiveData<List<Product>> = careStepType.switchMap {
        val input = ShowProductsToSelect.Input(it?.matchingApplications)
        showProductsToSelect(input).asLiveData()
    }

    val noProducts: LiveData<Boolean> = products.map { it.isEmpty() }

    fun selectProductApplicationType(type: CareStep.Type?) {
        careStepType.value = type
    }
}
package com.example.hairapp.page_care_form

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.domain.Product
import com.example.core.domain.ProductsProportion
import com.example.core.use_case.AddCare
import com.example.core.use_case.ShowSelectedProduct
import com.example.hairapp.framework.RecyclerLiveAdapter
import com.example.hairapp.framework.updateState
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

class CareFormViewModel @ViewModelInject constructor(
    private val showSelectedProduct: ShowSelectedProduct,
    private val addCare: AddCare
) : ViewModel() {
    companion object {
        private const val OMO = "OMO"
        private const val CG = "CG"
        private const val CUSTOM = "Własne"
    }

    private val _productsProportion = MediatorLiveData<ProductsProportion>()

    val careOptions: LiveData<Array<String>> = liveData {
        emit(arrayOf(OMO, CG, CUSTOM))
    }

    val date = MutableLiveData<String>()

    val careMethod = MutableLiveData<String>()

    val steps: LiveData<List<CareProduct>> = careMethod.map {
        when (it) {
            OMO -> Care.Type.OMO.makeSteps()
            CG -> Care.Type.CG.makeSteps()
            CUSTOM -> Care.Type.CUSTOM.makeSteps()
            else -> throw IllegalStateException("Not matching careMethod: $careMethod")
        }
    }

    val productsProportion: LiveData<ProductsProportion> = _productsProportion

    init {
        date.value = LocalDate.now().toString()
        careMethod.value = OMO
    }

    fun addProductsProportionSource(source: LiveData<ProductsProportion>) {
        _productsProportion.addSource(source) {
            _productsProportion.value = it
        }
    }

    suspend fun findProduct(productId: Int): Product? {
        val input = ShowSelectedProduct.Input(productId)
        return showSelectedProduct(input).firstOrNull()
    }

    suspend fun saveCare(steps: List<CareProduct>): Result<Unit> {
        if (steps.isEmpty()) {
            val exception = IllegalStateException("Nie zdefiniowano żadnych korków")
            return Result.failure(exception)
        }

        val selectedDate = date.value?.let { LocalDate.parse(it) }
        if (selectedDate == null) {
            val exception = IllegalStateException("Nie wybrano daty pielęgnacji")
            return Result.failure(exception)
        }

        val selectedCareMethod = careMethod.value
        if (selectedCareMethod == null) {
            val exception = IllegalStateException("Nie wybrano metody pielęgnacji")
            return Result.failure(exception)
        }

        val input = when (selectedCareMethod) {
            OMO -> AddCare.Input(selectedDate, Care.Type.OMO, steps)
            CG -> AddCare.Input(selectedDate, Care.Type.CG, steps)
            CUSTOM -> AddCare.Input(selectedDate, Care.Type.CUSTOM, steps)
            else -> throw IllegalStateException("Not matching careMethod: $careMethod")
        }

        return addCare(input)
    }
}
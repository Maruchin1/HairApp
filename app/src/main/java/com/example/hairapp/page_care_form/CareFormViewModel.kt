package com.example.hairapp.page_care_form

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.domain.Product
import com.example.core.use_case.AddCare
import com.example.core.use_case.ShowSelectedProduct
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

class CareFormViewModel @ViewModelInject constructor(
    private val showSelectedProduct: ShowSelectedProduct,
    private val addCare: AddCare
) : ViewModel() {
    companion object {
        private const val OMO = "OMO"
        private const val CG = "CG"
        private const val CUSTOM = "Własne"
    }

    val careOptions: LiveData<Array<String>> = liveData {
        emit(arrayOf(OMO, CG, CUSTOM))
    }

    val date = MutableLiveData<String>()

    val careMethod = MutableLiveData<String>()

    val steps: LiveData<List<CareProduct>> = careMethod.map {
        val care = when (it) {
            OMO -> Care.OMO()
            CG -> Care.CG()
            CUSTOM -> Care.Custom()
            else -> throw IllegalStateException("Not matching careMethod: $careMethod")
        }
        care.steps
    }

    init {
        date.value = LocalDate.now().toString()
        careMethod.value = OMO
    }

    suspend fun findProduct(productId: Int): Product? {
        val input = ShowSelectedProduct.Input(productId)
        return showSelectedProduct(input).firstOrNull()
    }

    suspend fun saveCare(steps: List<CareProduct>): Result<Unit> {
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
            OMO -> AddCare.Input.OMO(selectedDate, steps)
            CG -> AddCare.Input.CG(selectedDate, steps)
            CUSTOM -> AddCare.Input.Custom(selectedDate, steps)
            else -> throw IllegalStateException("Not matching careMethod: $careMethod")
        }

        return addCare(input)
    }
}
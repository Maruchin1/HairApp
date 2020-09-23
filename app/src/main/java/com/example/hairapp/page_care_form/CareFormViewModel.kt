package com.example.hairapp.page_care_form

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.domain.Product
import com.example.core.domain.ProductsProportion
import com.example.core.use_case.AddCare
import com.example.core.use_case.ShowSelectedCare
import com.example.core.use_case.ShowSelectedProduct
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

class CareFormViewModel @ViewModelInject constructor(
    private val showSelectedProduct: ShowSelectedProduct,
    private val showSelectedCare: ShowSelectedCare,
    private val addCare: AddCare
) : ViewModel() {

    private val _date = MutableLiveData(LocalDate.now())
    private val _careType = MutableLiveData(Care.Type.OMO)
    private val _steps = MediatorLiveData<List<CareProduct>>()
    private val _productsProportion = MediatorLiveData<ProductsProportion>()
    private var editCareId: Int? = null

    val careOptions: LiveData<Array<Care.Type>> = liveData { emit(Care.Type.values()) }
    val date: LiveData<LocalDate> = _date
    val careType: LiveData<Care.Type> = _careType
    val steps: LiveData<List<CareProduct>> = _steps
    val productsProportion: LiveData<ProductsProportion> = _productsProportion

    init {
        _steps.addSource(careType) {
            _steps.value = it.makeSteps()
        }
    }

    fun setEditCareAsync(careId: Int): Deferred<Result<Unit>> = viewModelScope.async {
        editCareId = careId
        val input = ShowSelectedCare.Input(careId)
        showSelectedCare(input).runCatching {
            val care = this.first()
            applyCareToEdit(care)
        }
    }

    fun setDate(date: LocalDate) {
        _date.value = date
    }

    fun setCareType(type: Care.Type) {
        _careType.value = type
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
        val selectedDate = date.value
        if (selectedDate == null) {
            val exception = IllegalStateException("Nie wybrano daty pielęgnacji")
            return Result.failure(exception)
        }

        val selectedCareType = careType.value
        if (selectedCareType == null) {
            val exception = IllegalStateException("Nie wybrano metody pielęgnacji")
            return Result.failure(exception)
        }

        if (steps.isEmpty()) {
            val exception = IllegalStateException("Nie zdefiniowano żadnych korków")
            return Result.failure(exception)
        }

        val input = AddCare.Input(selectedDate, selectedCareType, steps)
        return addCare(input)
    }

    private fun applyCareToEdit(care: Care) {
        _date.postValue(care.date)
        _careType.postValue(care.type)
        _steps.postValue(care.steps)
    }
}
package com.example.hairapp.page_care

import androidx.lifecycle.*
import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.domain.Product
import com.example.core.domain.ProductsProportion
import com.example.core.use_case.AddCare
import com.example.core.use_case.ShowSelectedCare
import com.example.core.use_case.ShowSelectedProduct
import com.example.core.use_case.UpdateCare
import com.example.hairapp.framework.resultFailure
import com.example.hairapp.framework.updateState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

class CareViewModel(
    private val showSelectedProduct: ShowSelectedProduct,
    private val showSelectedCare: ShowSelectedCare,
    private val addCare: AddCare,
    private val updateCare: UpdateCare
) : ViewModel() {

    private val _date = MutableLiveData(LocalDate.now())
    private val _careType = MutableLiveData(Care.Type.OMO)
    private val _steps = MediatorLiveData<List<CareProduct>>()
    private val _photos = MutableLiveData<MutableList<String>>(mutableListOf())
    private val _productsProportion = MediatorLiveData<ProductsProportion>()
    private var editCareId: Int? = null

    val careOptions: LiveData<Array<Care.Type>> = liveData { emit(Care.Type.values()) }
    val date: LiveData<LocalDate> = _date
    val careType: LiveData<Care.Type> = _careType
    val photos: LiveData<List<String>> = _photos.map { it.toList() }
    val photosAvailable: LiveData<Boolean> = photos.map { it.isNotEmpty() }
    val steps: LiveData<List<CareProduct>> = _steps
    val stepsAvailable: LiveData<Boolean> = steps.map { it.isNotEmpty() }
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

    fun addPhoto(photo: String) {
        _photos.updateState { it.add(photo) }
    }

    fun deletePhoto(photo: String) {
        _photos.updateState { it.remove(photo) }
    }

    suspend fun findProduct(productId: Int): Product? {
        val input = ShowSelectedProduct.Input(productId)
        return showSelectedProduct(input).firstOrNull()
    }

    suspend fun saveCare(steps: List<CareProduct>): Result<Unit> {
        val selectedDate = date.value
            ?: return resultFailure("Nie wybrano daty pielęgnacji")
        val selectedCareType = careType.value
            ?: return resultFailure("Nie wybrano metody pielęgnacji")
        val photos = _photos.value ?: emptyList()
        return if (editCareId == null) {
            val input = AddCare.Input(selectedDate, selectedCareType, photos, steps)
            addCare(input)
        } else {
            val input = UpdateCare.Input(editCareId!!, selectedDate, photos, steps)
            updateCare(input)
        }
    }

    private fun applyCareToEdit(care: Care) {
        _date.postValue(care.date)
        _careType.postValue(care.type)
        _photos.postValue(care.photos.toMutableList())
        _steps.postValue(care.steps)
    }
}
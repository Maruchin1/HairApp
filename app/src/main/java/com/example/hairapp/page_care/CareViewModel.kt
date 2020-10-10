package com.example.hairapp.page_care

import androidx.lifecycle.*
import com.example.core.domain.Care
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import com.example.core.domain.ProductsProportion
import com.example.core.use_case.*
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
    private val updateCare: UpdateCare,
    private val deleteCare: DeleteCare
) : ViewModel() {

    private val _editCareId = MutableLiveData<Int?>(null)
    private val _date = MutableLiveData(LocalDate.now())
    private val _steps = MediatorLiveData<List<CareStep>>()
    private val _photos = MutableLiveData<MutableList<String>>(mutableListOf())
    private val _productsProportion = MediatorLiveData<ProductsProportion>()

    val date: LiveData<LocalDate> = _date
    val photos: LiveData<List<String>> = _photos.map { it.toList() }
    val noPhotos: LiveData<Boolean> = photos.map { it.isEmpty() }
    val steps: LiveData<List<CareStep>> = _steps
    val noSteps: LiveData<Boolean> = steps.map { it.isEmpty() }
    val stepsAvailable: LiveData<Boolean> = steps.map { it.isNotEmpty() }
    val productsProportion: LiveData<ProductsProportion> = _productsProportion

    fun setEditCareAsync(careId: Int): Deferred<Result<Unit>> = viewModelScope.async {
        _editCareId.postValue(careId)
        val input = ShowSelectedCare.Input(careId)
        showSelectedCare(input).runCatching {
            val care = this.first()
            applyCareToEdit(care)
        }
    }

    fun setDate(date: LocalDate) {
        _date.value = date
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

    suspend fun saveCare(steps: List<CareStep>): Result<Unit> {
        val selectedDate = date.value
            ?: return resultFailure("Nie wybrano daty pielęgnacji")
        val photos = _photos.value ?: emptyList()
        val editCareId = _editCareId.value
        return if (editCareId == null) {
            val input = AddCare.Input(selectedDate, photos, steps)
            addCare(input)
        } else {
            val input = UpdateCare.Input(editCareId, selectedDate, photos, steps)
            updateCare(input)
        }
    }

    suspend fun deleteCare(): Result<Unit> {
        val careId = _editCareId.value
            ?: return resultFailure("Can't delete when not in edit mode")
        val input = DeleteCare.Input(careId)
        return deleteCare(input)
    }

    private fun applyCareToEdit(care: Care) {
        _date.postValue(care.date)
        _photos.postValue(care.photos.toMutableList())
        _steps.postValue(care.steps)
    }
}
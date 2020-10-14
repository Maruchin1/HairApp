package com.example.hairapp.page_care

import androidx.lifecycle.*
import com.example.core.domain.*
import com.example.core.use_case.*
import com.example.hairapp.framework.resultFailure
import com.example.hairapp.framework.updateState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

class CareViewModel(
    private val showSelectedProduct: ShowSelectedProduct,
    private val showSelectedCare: ShowSelectedCare,
    private val showCareSchema: ShowCareSchema,
    private val addCare: AddCare,
    private val updateCare: UpdateCare,
    private val deleteCare: DeleteCare
) : ViewModel() {

    private val _editCareId = MutableLiveData<Int?>(null)
    private val _schemaName = MutableLiveData(CareSchema.noSchema.name)
    private val _date = MutableLiveData(LocalDate.now())
    private val _steps = MediatorLiveData<List<CareStep>>()
    private val _photos = MutableLiveData<MutableList<String>>(mutableListOf())
    private val _productsProportion = MediatorLiveData<ProductsProportion>()

    val title: LiveData<String> = _schemaName
    val date: LiveData<LocalDate> = _date
    val photos: LiveData<List<String>> = _photos.map { it.toList() }
    val noPhotos: LiveData<Boolean> = photos.map { it.isEmpty() }
    val steps: LiveData<List<CareStep>> = _steps
    val productsProportion: LiveData<ProductsProportion> = _productsProportion

    suspend fun setNewCareSchema(schemaId: Int): Result<Unit> {
        val input = ShowCareSchema.Input(schemaId)
        return showCareSchema(input).runCatching {
            val schema = this.first()
            _schemaName.postValue(schema.name)
            _steps.postValue(schema.steps)
        }
    }

    suspend fun setEditCare(careId: Int): Result<Unit> {
        _editCareId.postValue(careId)
        val input = ShowSelectedCare.Input(careId)
        return showSelectedCare(input).runCatching {
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
        val schemaName = _schemaName.value!!
        val selectedDate = _date.value!!
        val photos = _photos.value ?: emptyList()
        val editCareId = _editCareId.value
        return if (editCareId == null) {
            val input = AddCare.Input(schemaName, selectedDate, photos, steps)
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
package com.example.care_details.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.care_details.use_case.ChangeCareDateUseCase
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.*
import com.example.corev2.relations.CareStepWithProduct
import com.example.corev2.service.ClockService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

internal class CareDetailsViewModel(
    private val clockService: ClockService,
    private val careDao: CareDao,
    private val changeCareDateUseCase: ChangeCareDateUseCase
) : ViewModel() {

    private val careIdState = MutableStateFlow<Long?>(null)
    private val careState = careIdState
        .filterNotNull()
        .flatMapLatest { careDao.getById(it) }
    private val careDateState = MutableStateFlow(clockService.getNow())
    private val schemaNameState = MutableStateFlow("")
    private val stepsState = MutableStateFlow(listOf<CareStepWithProduct>())
    private val notesState = MutableStateFlow("")
    private val photosState = MutableStateFlow(listOf<CarePhoto>())

    val careDate: LiveData<LocalDateTime> = careDateState.asLiveData()
    val schemaName: LiveData<String> = schemaNameState.asLiveData()
    val steps: LiveData<List<CareStepWithProduct>> = stepsState.asLiveData()
    val notes: LiveData<String> = notesState.asLiveData()
    val photos: LiveData<List<CarePhoto>> = photosState.asLiveData()

    init {
        viewModelScope.launch {
            careState.filterNotNull().collect {
                careDateState.emit(it.care.date)
                schemaNameState.emit(it.care.schemaName)
                stepsState.emit(it.steps)
                notesState.emit(it.care.notes)
                photosState.emit(it.photos)
            }
        }
    }

    fun onCareSelected(careId: Long) = viewModelScope.launch {
        careIdState.emit(careId)
    }

    fun onChangeDateClicked() = viewModelScope.launch {
        careState.firstOrNull()?.let {
            changeCareDateUseCase.invoke(it.care)
        }
    }
}
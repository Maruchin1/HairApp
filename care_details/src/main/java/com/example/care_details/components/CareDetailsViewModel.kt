package com.example.care_details.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.*
import com.example.corev2.relations.CareStepWithProduct
import com.example.corev2.service.ClockService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate

internal class CareDetailsViewModel(
    private val clockService: ClockService,
    private val careDao: CareDao
) : ViewModel() {

    private val careDateState = MutableStateFlow(clockService.getNow())
    private val schemaNameState = MutableStateFlow("")
    private val stepsState = MutableStateFlow(listOf<CareStepWithProduct>())
    private val notesState = MutableStateFlow("")
    private val photosState = MutableStateFlow(listOf<CarePhoto>())

    val careDate: LiveData<LocalDate> = careDateState.asLiveData()
    val schemaName: LiveData<String> = schemaNameState.asLiveData()
    val steps: LiveData<List<CareStepWithProduct>> = stepsState.asLiveData()
    val notes: LiveData<String> = notesState.asLiveData()
    val photos: LiveData<List<CarePhoto>> = photosState.asLiveData()

    fun onCareDateSelected(newDate: LocalDate) = viewModelScope.launch {
        careDateState.emit(newDate)
    }

    fun onCareSelected(careId: Long) = viewModelScope.launch {
        careDao
            .getById(careId)
            .firstOrNull()
            ?.let {
                schemaNameState.emit(it.care.schemaName)
                stepsState.emit(it.steps)
                notesState.emit(it.care.notes)
                photosState.emit(it.photos)
            }
    }
}
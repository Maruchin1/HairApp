package com.example.care_details.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.Care
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.CareStep
import com.example.corev2.service.ClockService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate

internal class CareDetailsViewModel(
    private val clockService: ClockService,
    private val careSchemaDao: CareSchemaDao
) : ViewModel() {

    private val careDateState = MutableStateFlow(clockService.getNow())
    private val schemaNameState = MutableStateFlow("")
    private val careStepsState = MutableStateFlow(listOf<CareStep>())
    private val notesState = MutableStateFlow("")

    val careDate: LiveData<LocalDate> = careDateState.asLiveData()
    val schemaName: LiveData<String> = schemaNameState.asLiveData()
    val careSteps: LiveData<List<CareStep>> = careStepsState.asLiveData()
    val notes: LiveData<String> = notesState.asLiveData()

    fun onCareDateSelected(newDate: LocalDate) = viewModelScope.launch {
        careDateState.emit(newDate)
    }

    fun onSchemaSelected(schemaId: Long) = viewModelScope.launch {
        careSchemaDao
            .getById(schemaId)
            .firstOrNull()
            ?.let { schemaWithSteps ->
                schemaNameState.emit(schemaWithSteps.careSchema.name)
                applyStepsFromSchema(schemaWithSteps.steps)
            }
    }

    private suspend fun applyStepsFromSchema(schemaSteps: List<CareSchemaStep>) {
        val careSteps = schemaSteps
            .map { it.toCareStep() }
            .sortedBy { it.order }
        careStepsState.emit(careSteps)
    }
}
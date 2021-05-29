package com.example.care_details.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.corev2.service.ClockService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

internal class CareDetailsViewModel(
    private val clockService: ClockService
) : ViewModel() {

    private val careDateState = MutableStateFlow<LocalDate>(clockService.getNow())

    val careDate: LiveData<LocalDate> = careDateState.asLiveData()

    fun onCareDateSelected(newDate: LocalDate) = viewModelScope.launch {
        careDateState.emit(newDate)
    }
}
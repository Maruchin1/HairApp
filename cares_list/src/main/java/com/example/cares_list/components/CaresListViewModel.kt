package com.example.cares_list.components

import android.app.Activity
import android.util.Log
import androidx.lifecycle.*
import com.example.cares_list.use_case.AddNewCareUseCase
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care
import com.example.corev2.relations.CareWithStepsAndPhotos
import com.example.corev2.service.ClockService
import com.example.corev2.service.daysBetween
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDate

internal class CaresListViewModel(
    private val careDao: CareDao,
    private val clockService: ClockService,
    private val addNewCareUseCase: AddNewCareUseCase
) : ViewModel() {

    private val orderedCaresFlow = careDao.getAllCares()
        .map { sortCaresFromNewest(it) }

    val today: LiveData<LocalDate> = liveData {
        emit(clockService.getNow())
    }

    val daysFromLastCare: LiveData<Long> = orderedCaresFlow
        .map { it.firstOrNull() }
        .map { calcDaysFromLastCare(it?.care) }
        .onStart { emit(0) }
        .asLiveData()

    val orderedCares: LiveData<List<CareWithStepsAndPhotos>> = orderedCaresFlow.asLiveData()

    val noCares: LiveData<Boolean> = orderedCaresFlow
        .map { it.isEmpty() }
        .asLiveData()

    fun onAddCareClick(activity: Activity) = viewModelScope.launch {
        addNewCareUseCase(activity)
    }

    private fun sortCaresFromNewest(cares: List<CareWithStepsAndPhotos>): List<CareWithStepsAndPhotos> {
        return cares.sortedByDescending { it.care.date }
    }

    private fun calcDaysFromLastCare(lastCare: Care?): Long {
        if (lastCare == null) return 0
        return lastCare.date.daysBetween(clockService.getNow())
    }
}
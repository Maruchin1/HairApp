package com.example.cares_list.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care
import com.example.corev2.service.ClockService
import com.example.corev2.service.daysBetween
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.LocalDate

internal class CaresListViewModel(
    private val careDao: CareDao,
    private val clockService: ClockService
) : ViewModel() {

    private val orderedCaresFlow = careDao.getAllCares()
        .map { sortCaresFromNewest(it) }

    val today: LiveData<LocalDate> = liveData {
        emit(clockService.getNow())
    }

    val daysFromLastCare: LiveData<Long> = orderedCaresFlow
        .map { it.firstOrNull() }
        .map { calcDaysFromLastCare(it) }
        .onStart { emit(0) }
        .asLiveData()

    val orderedCares: LiveData<List<Care>> = orderedCaresFlow.asLiveData()

    val noCares: LiveData<Boolean> = orderedCaresFlow
        .map { it.isEmpty() }
        .asLiveData()

    private fun sortCaresFromNewest(cares: List<Care>): List<Care> {
        return cares.sortedByDescending { it.date }
    }

    private fun calcDaysFromLastCare(lastCare: Care?): Long {
        if (lastCare == null) return 0
        return lastCare.date.daysBetween(clockService.getNow())
    }
}
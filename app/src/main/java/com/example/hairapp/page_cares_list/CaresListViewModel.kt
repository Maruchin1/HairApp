package com.example.hairapp.page_cares_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.core.domain.Care
import com.example.core.domain.CareSchema
import com.example.core.gateway.CareRepo
import com.example.core.gateway.CareSchemaRepo
import com.example.core.util.AppClock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.startWith
import java.time.LocalDate

class CaresListViewModel(
    private val careRepo: CareRepo,
    private val appClock: AppClock,
    private val careSchemaRepo: CareSchemaRepo
) : ViewModel() {

    private val _orderedCares = careRepo.findAll()
        .map { sortCaresFromNewest(it) }
    private val allCareSchemas = careSchemaRepo.findAll()

    val today: LiveData<LocalDate> = liveData {
        emit(appClock.now())
    }

    val daysFromLastCare: LiveData<Long> = _orderedCares
        .map { it.firstOrNull() }
        .map { calcDaysFromLastCare(it) }
        .onStart { emit(0) }
        .asLiveData()

    val orderedCares: LiveData<List<Care>> = _orderedCares
        .asLiveData()

    val noCares: LiveData<Boolean> = _orderedCares
        .map { it.isEmpty() }
        .asLiveData()

    suspend fun getCareSchemas(): List<CareSchema> {
        return allCareSchemas.first()
    }

    private fun sortCaresFromNewest(cares: List<Care>): List<Care> {
        return cares.sortedByDescending { it.date }
    }

    private fun calcDaysFromLastCare(lastCare: Care?): Long {
        if (lastCare == null) return 0
        return appClock.daysUntilToday(lastCare.date)
    }
}
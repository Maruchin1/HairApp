package com.example.hairapp.page_peh_balance

import androidx.lifecycle.*
import com.example.core.domain.Care
import com.example.core.domain.CaresLimit
import com.example.core.domain.PehBalance
import com.example.core.gateway.CareRepo
import com.example.core.gateway.AppPreferences
import com.example.core.util.AppClock
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PehBalanceViewModel(
    private val careRepo: CareRepo,
    private val appPreferences: AppPreferences,
    private val appClock: AppClock
) : ViewModel() {

    private val allCares: Flow<List<Care>> = careRepo.findAll()
    private val _caresLimit: Flow<CaresLimit> = appPreferences.getPehBalanceCaresLimit()

    val caresLimit: LiveData<CaresLimit> = _caresLimit.asLiveData()

    val pehBalance: LiveData<PehBalance> = _caresLimit
        .flatMapLatest { calcPehBalance(it) }
        .asLiveData()

    val numOfLastMonthCares: LiveData<Int> = allCares
        .mapLatest { countCaresFromLastMonth(it) }
        .asLiveData()

    val avgDaysIntervalBetweenCares: LiveData<Long> = allCares
        .mapLatest { calcAvgDaysIntervalBetweenCares(it) }
        .asLiveData()

    fun setCaresForBalance(value: CaresLimit) = viewModelScope.launch {
        appPreferences.setPehBalanceCaresLimit(value)
    }

    suspend fun getCaresForBalance(): CaresLimit {
        return _caresLimit.first()
    }

    private fun calcPehBalance(caresLimit: CaresLimit): Flow<PehBalance> {
        return loadCaresWithLimit(caresLimit)
            .map { calcBalanceForEach(it) }
            .map { calcAvgBalance(it) }
    }

    private fun loadCaresWithLimit(caresLimit: CaresLimit): Flow<List<Care>> {
        return when (caresLimit) {
            CaresLimit.ALL -> allCares
            else -> careRepo.findLastN(caresLimit.daysLimit)
        }
    }

    private fun calcBalanceForEach(schemas: List<Care>): List<PehBalance> {
        return schemas.map { PehBalance.fromSteps(it.steps) }
    }

    private fun calcAvgBalance(balances: List<PehBalance>): PehBalance {
        val numOfAll = balances.size
        val avgHumectants = balances.sumByDouble { it.humectants } / numOfAll
        val avgEmollients = balances.sumByDouble { it.emollients } / numOfAll
        val avgProteins = balances.sumByDouble { it.proteins } / numOfAll
        return PehBalance(avgHumectants, avgEmollients, avgProteins)
    }

    private fun countCaresFromLastMonth(cares: List<Care>): Int {
        return cares.count { appClock.isFromLastDays(it.date, 30) }
    }

    private fun calcAvgDaysIntervalBetweenCares(cares: List<Care>): Long {
        return cares
            .sortedBy { it.date }
            .zipWithNext { a, b -> calcDaysBetweenCares(a, b) }
            .average()
            .toLong()
    }

    private fun calcDaysBetweenCares(firstCare: Care, secondCare: Care): Long {
        return appClock.absoluteDaysBetween(firstCare.date, secondCare.date)
    }
}
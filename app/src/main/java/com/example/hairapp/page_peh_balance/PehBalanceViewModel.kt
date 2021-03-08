package com.example.hairapp.page_peh_balance

import androidx.lifecycle.*
import com.example.core.domain.Care
import com.example.core.domain.CaresForBalance
import com.example.core.domain.PehBalance
import com.example.core.gateway.CareRepo
import com.example.core.gateway.AppPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PehBalanceViewModel(
    private val careRepo: CareRepo,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val allCares: Flow<List<Care>> = careRepo.findAll()
    private val _caresForBalance: Flow<CaresForBalance> = appPreferences.getCaresForBalance()

    val caresForBalance: LiveData<CaresForBalance> = _caresForBalance.asLiveData()

    val pehBalance: LiveData<PehBalance> = _caresForBalance
        .flatMapLatest { calcPehBalance(it) }
        .asLiveData()

    val numOfLastMonthCares: LiveData<Int> = allCares
        .mapLatest { countCaresFromLastMonth(it) }
        .asLiveData()

    val avgDaysIntervalBetweenCares: LiveData<Int> = allCares
        .mapLatest { calcAvgDaysIntervalBetweenCares(it) }
        .asLiveData()

    fun setCaresForBalance(value: CaresForBalance) = viewModelScope.launch {
        appPreferences.setCaresForBalance(value)
    }

    suspend fun getCaresForBalance(): CaresForBalance {
        return _caresForBalance.first()
    }

    private fun calcPehBalance(caresForBalance: CaresForBalance): Flow<PehBalance> {
        return loadCaresForPehBalance(caresForBalance)
            .map { calcBalanceForEach(it) }
            .map { calcAvgBalance(it) }
    }

    private fun loadCaresForPehBalance(caresForBalance: CaresForBalance): Flow<List<Care>> {
        return when (caresForBalance) {
            CaresForBalance.ALL -> careRepo.findAll()
            else -> careRepo.findLastN(caresForBalance.daysLimit)
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
        return cares.count { it.isFromLastDays(30) }
    }

    private fun calcAvgDaysIntervalBetweenCares(cares: List<Care>): Int {
        return cares
            .sortedBy { it.date }
            .zipWithNext { a, b -> b.daysFromCare(a) }
            .average()
            .toInt()
    }


}
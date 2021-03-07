package com.example.hairapp.page_peh_balance

import androidx.lifecycle.*
import com.example.core.domain.Care
import com.example.core.domain.PehBalance
import com.example.core.gateway.CareRepo
import kotlinx.coroutines.flow.*

class PehBalanceViewModel(
    private val careRepo: CareRepo
) : ViewModel() {

    private val _selectedNumOfCares = MutableLiveData(5)

    val selectedNumOfCares: LiveData<Int> = _selectedNumOfCares

    val pehBalance: LiveData<PehBalance> = _selectedNumOfCares.asFlow()
        .flatMapLatest { calcPehBalance(it) }
        .asLiveData()

    val numOfLastMonthCares: LiveData<Int> = careRepo.findAll()
        .mapLatest { countCaresFromLastMonth(it) }
        .asLiveData()

    val avgDaysIntervalBetweenCares: LiveData<Int> = careRepo.findAll()
        .mapLatest { calcAvgDaysIntervalBetweenCares(it) }
        .asLiveData()

    private fun calcPehBalance(numOfCares: Int): Flow<PehBalance> {
        return careRepo.findLastN(numOfCares)
            .map { calcBalanceForEach(it) }
            .map { calcAvgBalance(it) }
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
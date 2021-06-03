package com.example.peh_balance

import androidx.lifecycle.*
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care
import com.example.corev2.entities.CaresLimit
import com.example.corev2.entities.PehBalance
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PehBalanceViewModel(
    private val careDao: CareDao,
//    private val appPreferences: AppPreferences,
//    private val appClock: AppClock
) : ViewModel() {

//    private val allCares: Flow<List<Care>> = careDao.getAllCares()
//    private val _caresLimit: Flow<CaresLimit> = appPreferences.getPehBalanceCaresLimit()
//    private val limitedCares: Flow<List<Care>> = _caresLimit
//        .flatMapLatest { loadCaresWithLimit(it) }

//    val caresLimit: LiveData<CaresLimit> = _caresLimit.asLiveData()

//    val pehBalance: LiveData<PehBalance> = limitedCares
//        .mapLatest { calcPehBalance(it) }
//        .asLiveData()

//    val numOfLastMonthCares: LiveData<Int> = limitedCares
//        .mapLatest { countCaresFromLastMonth(it) }
//        .asLiveData()

//    val avgDaysIntervalBetweenCares: LiveData<Long> = limitedCares
//        .mapLatest { calcAvgDaysIntervalBetweenCares(it) }
//        .asLiveData()

//    fun setCaresForBalance(value: CaresLimit) = viewModelScope.launch {
//        appPreferences.setPehBalanceCaresLimit(value)
//    }

//    suspend fun getCaresForBalance(): CaresLimit {
//        return _caresLimit.first()
//    }

//    private fun calcPehBalance(cares: List<Care>): PehBalance {
//        val pehBalances = calcPehBalanceForEach(cares)
//        return calcAvgBalance(pehBalances)
//    }

//    private fun loadCaresWithLimit(caresLimit: CaresLimit): Flow<List<Care>> {
//        return when (caresLimit) {
//            CaresLimit.ALL -> allCares
//            else -> careRepo.findLastN(caresLimit.daysLimit)
//        }
//    }

//    private fun calcPehBalanceForEach(schemas: List<Care>): List<PehBalance> {
//        return schemas.map { PehBalance.fromSteps(it.steps) }
//    }

    private fun calcAvgBalance(balances: List<PehBalance>): PehBalance {
        val numOfAll = balances.size
        val avgHumectants = balances.sumByDouble { it.humectants } / numOfAll
        val avgEmollients = balances.sumByDouble { it.emollients } / numOfAll
        val avgProteins = balances.sumByDouble { it.proteins } / numOfAll
        return PehBalance(avgHumectants, avgEmollients, avgProteins)
    }

//    private fun countCaresFromLastMonth(cares: List<Care>): Int {
//        return cares.count { appClock.isFromLastDays(it.date, 30) }
//    }

//    private fun calcAvgDaysIntervalBetweenCares(cares: List<Care>): Long {
//        return cares
//            .sortedBy { it.date }
//            .zipWithNext { a, b -> calcDaysBetweenCares(a, b) }
//            .average()
//            .toLong()
//    }

//    private fun calcDaysBetweenCares(firstCare: Care, secondCare: Care): Long {
//        return appClock.absoluteDaysBetween(firstCare.date, secondCare.date)
//    }
}
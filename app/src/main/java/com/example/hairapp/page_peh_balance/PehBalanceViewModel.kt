package com.example.hairapp.page_peh_balance

import androidx.lifecycle.*
import com.example.core.domain.PehBalance
import com.example.core.use_case.ShowPehBalance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class PehBalanceViewModel(
    private val showPehBalance: ShowPehBalance
) : ViewModel() {

    private val _selectedNumOfCares = MutableLiveData(5)

    val selectedNumOfCares: LiveData<Int> = _selectedNumOfCares

    val pehBalance: LiveData<PehBalance> = _selectedNumOfCares.asFlow()
        .flatMapLatest { loadPehBalance(it) }
        .asLiveData()

    private fun loadPehBalance(numOfCares: Int): Flow<PehBalance> {
        val input = ShowPehBalance.Input(numOfCares)
        return showPehBalance(input)
    }
}
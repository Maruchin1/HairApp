package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.CareSchema
import com.example.core.domain.PehBalance
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShowPehBalance(
    private val careSchemaRepo: CareSchemaRepo
) : FlowUseCase<ShowPehBalance.Input, PehBalance>() {

    override fun execute(input: Input): Flow<PehBalance> {
        return careSchemaRepo.findLastN(input.numOfCares)
            .map { calcBalanceForEach(it) }
            .map { calcAvgBalance(it) }
    }

    private fun calcBalanceForEach(schemas: List<CareSchema>): List<PehBalance> {
        return schemas.map { PehBalance.fromSteps(it.steps) }
    }

    private fun calcAvgBalance(balances: List<PehBalance>): PehBalance {
        val numOfAll = balances.size
        val avgHumectants = balances.sumByDouble { it.humectants } / numOfAll
        val avgEmollients = balances.sumByDouble { it.emollients } / numOfAll
        val avgProteins = balances.sumByDouble { it.proteins } / numOfAll
        return PehBalance(avgHumectants, avgEmollients, avgProteins)
    }

    data class Input(val numOfCares: Int)
}
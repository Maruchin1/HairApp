package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.Care
import com.example.core.gateway.CareRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShowCaresList(
    private val careRepo: CareRepo
) : FlowUseCase<Unit, List<Care>>() {

    override fun execute(input: Unit): Flow<List<Care>> {
        return careRepo.findAll().map { list ->
            list.sortedByDescending { it.date }
        }
    }
}
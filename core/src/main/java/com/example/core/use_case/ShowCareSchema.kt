package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.CareStep
import com.example.core.gateway.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShowCareSchema(
    private val settings: Settings
) : FlowUseCase<Unit, List<CareStep>>() {

    override fun execute(input: Unit): Flow<List<CareStep>> {
        return settings.getCareSchema()
            .map { list -> list.sortedBy { it.order } }
    }
}
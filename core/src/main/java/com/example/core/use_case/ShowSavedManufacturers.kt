package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.gateway.Settings
import kotlinx.coroutines.flow.Flow

class ShowSavedManufacturers(
    private val settings: Settings
) : FlowUseCase<Unit, List<String>>() {

    override fun execute(input: Unit): Flow<List<String>> {
        return settings.findSavedManufacturers()
    }

}
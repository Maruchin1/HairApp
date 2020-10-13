package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.CareStep
import com.example.core.gateway.Settings
import kotlinx.coroutines.flow.first

class SaveCareSchema(
    private val settings: Settings
) : UseCase<SaveCareSchema.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        val currSchema = getCurrSchema()
        if (currSchema != input.schema) {
            saveSchema()
        }
    }

    private suspend fun getCurrSchema(): List<CareStep> {
        return settings.getCareSchema().first()
    }

    private suspend fun saveSchema() {
        settings.setCareSchema(input.schema)
    }

    data class Input(val schema: List<CareStep>)
}
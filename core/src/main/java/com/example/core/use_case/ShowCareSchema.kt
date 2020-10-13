package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.Flow

class ShowCareSchema(
    private val careSchemaRepo: CareSchemaRepo
) : FlowUseCase<ShowCareSchema.Input, CareSchema>() {

    override fun execute(input: Input): Flow<CareSchema> {
        return careSchemaRepo.findById(input.id)
    }

    data class Input(val id: Int)
}
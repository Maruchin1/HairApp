package com.example.core.use_case

import com.example.core.base.FlowUseCase
import com.example.core.domain.CareSchema
import com.example.core.errors.CareSchemaException
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEmpty

class ShowCareSchema(
    private val careSchemaRepo: CareSchemaRepo
) : FlowUseCase<ShowCareSchema.Input, CareSchema>() {

    override fun execute(input: Input): Flow<CareSchema> {
        return careSchemaRepo.findById(input.schemaId)
            .onEmpty { throw schemaNotFound() }
    }

    private fun schemaNotFound(): Exception {
        return CareSchemaException.NotFound()
    }

    data class Input(val schemaId: Int)
}
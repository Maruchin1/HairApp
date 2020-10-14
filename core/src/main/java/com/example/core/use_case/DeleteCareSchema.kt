package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.firstOrNull

class DeleteCareSchema(
    private val careSchemaRepo: CareSchemaRepo
) : UseCase<DeleteCareSchema.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        findExisting()?.let {
           delete(it)
        }
    }

    private suspend fun findExisting(): CareSchema? {
        return careSchemaRepo.findById(input.schemaId).firstOrNull()
    }

    private suspend fun delete(careSchema: CareSchema) {
        careSchemaRepo.delete(careSchema)
    }

    data class Input(val schemaId: Int)
}
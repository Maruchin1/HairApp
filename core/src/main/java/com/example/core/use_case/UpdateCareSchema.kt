package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.CareSchema
import com.example.core.domain.CareStep
import com.example.core.errors.CareSchemaException
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.firstOrNull

class UpdateCareSchema(
    private val careSchemaRepo: CareSchemaRepo
) : UseCase<UpdateCareSchema.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        val existing = findExisting()
        if (existing == null) {
            notFound()
        } else {
            patchSchema(existing)
            saveSchema(existing)
        }
    }

    private suspend fun findExisting(): CareSchema? {
        return careSchemaRepo.findById(input.id).firstOrNull()
    }

    private fun notFound() {
        throw CareSchemaException.NotFound(input.name)
    }

    private fun patchSchema(schema: CareSchema) {
        schema.apply {
            name = input.name
            steps = input.steps
        }
    }

    private suspend fun saveSchema(schema: CareSchema) {
        careSchemaRepo.update(schema)
    }

    data class Input(val id: Int, val name: String, val steps: List<CareStep>)
}
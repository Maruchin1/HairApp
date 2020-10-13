package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.CareSchema
import com.example.core.errors.CareSchemaException
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.firstOrNull

class AddCareSchema(
    private val careSchemaRepo: CareSchemaRepo
) : UseCase<AddCareSchema.Input, Unit>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input) {
        this.input = input
        val withSameName = findWithSameName()
        if (withSameName != null) {
            alreadyExists()
        } else {
            saveSchema()
        }
    }

    private suspend fun findWithSameName(): CareSchema? {
        return careSchemaRepo.findByName(input.name).firstOrNull()
    }

    private fun alreadyExists() {
        throw CareSchemaException.AlreadyExists(input.name)
    }

    private suspend fun saveSchema() {
        val careSchema = CareSchema(
            id = 0,
            name = input.name,
            steps = emptyList()
        )
        careSchemaRepo.addNew(careSchema)
    }

    data class Input(val name: String)
}
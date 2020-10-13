package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.CareSchema
import com.example.core.errors.CareSchemaException
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.firstOrNull

class AddCareSchema(
    private val careSchemaRepo: CareSchemaRepo
) : UseCase<AddCareSchema.Input, Int>() {

    private lateinit var input: Input

    override suspend fun execute(input: Input): Int {
        val careSchema = CareSchema(
            id = 0,
            name = input.name,
            steps = emptyList()
        )
        return careSchemaRepo.addNew(careSchema)
    }

    data class Input(val name: String)
}
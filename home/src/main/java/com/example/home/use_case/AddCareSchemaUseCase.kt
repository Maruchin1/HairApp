package com.example.home.use_case

import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo

internal class AddCareSchemaUseCase(
    private val careSchemaRepo: CareSchemaRepo
) {

    suspend operator fun invoke(schemaName: String) {
        val newSchema = CareSchema(
            id = -1,
            name = schemaName,
            steps = listOf()
        )
        careSchemaRepo.addNew(newSchema)
    }
}
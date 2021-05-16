package com.example.care_schema_details.use_case

import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.firstOrNull

class DeleteSchemaUseCase(
    private val careSchemaRepo: CareSchemaRepo
) {

    suspend operator fun invoke(careSchemaId: Int) {
        careSchemaRepo
            .findById(careSchemaId)
            .firstOrNull()
            ?.let { careSchema ->
                careSchemaRepo.delete(careSchema)
            }
    }
}
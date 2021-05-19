package com.example.edit_care_schema.use_case

import com.example.common.repository.CareSchemaRepo
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
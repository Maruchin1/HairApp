package com.example.edit_care_schema.use_case

import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.firstOrNull

internal class ChangeSchemaNameUseCase(
    private val careSchemaRepo: CareSchemaRepo
) {

    suspend operator fun invoke(careSchemaId: Int, newName: String) {
        careSchemaRepo
            .findById(careSchemaId)
            .firstOrNull()
            ?.let { careSchema ->
                val update = careSchema.copy(name = newName)
                careSchemaRepo.update(update)
            }
    }
}
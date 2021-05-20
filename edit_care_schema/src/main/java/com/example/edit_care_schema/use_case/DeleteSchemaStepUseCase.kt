package com.example.edit_care_schema.use_case

import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.firstOrNull

internal class DeleteSchemaStepUseCase(
    private val careSchemaRepo: CareSchemaRepo
) {

    suspend operator fun invoke(careSchemaId: Int, stepId: Int) {
        careSchemaRepo
            .findById(careSchemaId)
            .firstOrNull()
            ?.let { careSchema ->
                val idxToDelete = careSchema.steps.indexOfFirst {
                    it.id == stepId
                }
                val newSteps = careSchema.steps.toMutableList()
                newSteps.removeAt(idxToDelete)
                careSchema.steps = newSteps
                careSchemaRepo.update(careSchema)
            }
    }
}
package com.example.edit_care_schema.use_case

import com.example.common.repository.CareSchemaRepo
import com.example.core.domain.CareSchemaStep
import kotlinx.coroutines.flow.firstOrNull

internal class ChangeSchemaStepsUseCase(
    private val careSchemaRepo: CareSchemaRepo
) {

    suspend operator fun invoke(careSchemaId: Int, newSteps: List<CareSchemaStep>) {
        careSchemaRepo
            .findById(careSchemaId)
            .firstOrNull()
            ?.let { careSchema ->
                val update = careSchema.copy(steps = newSteps)
                careSchemaRepo.update(update)
            }
    }
}
package com.example.edit_care_schema.use_case

import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import com.example.core.gateway.CareSchemaRepo
import kotlinx.coroutines.flow.firstOrNull

class AddSchemaStepUseCase(
    private val careSchemaRepo: CareSchemaRepo
) {

    suspend operator fun invoke(careSchemaId: Int, type: CareStep.Type) {
        careSchemaRepo
            .findById(careSchemaId)
            .firstOrNull()
            ?.let { careSchema ->
                val newSchemaStep = CareSchemaStep(
                    id = -1,
                    type = type,
                    order = careSchema.steps.size
                )
                val update = careSchema.copy(steps = careSchema.steps + newSchemaStep)
                careSchemaRepo.update(update)
            }
    }
}
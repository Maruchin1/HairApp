package com.example.edit_care_schema.use_case

import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchemaStep

internal class UpdateStepsOrderUseCase(
    private val careSchemaStepDao: CareSchemaStepDao
) {

    suspend operator fun invoke(steps: List<CareSchemaStep>) {
        steps.forEachIndexed { index, careSchemaStep ->
            careSchemaStep.order = index
        }
        careSchemaStepDao.update(*steps.toTypedArray())
    }
}
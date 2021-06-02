package com.example.care_details.use_case

import com.example.corev2.dao.CareStepDao
import com.example.corev2.entities.CareStep

internal class UpdateCareStepsOrderUseCase(
    private val careStepDao: CareStepDao
) {

    suspend operator fun invoke(steps: List<CareStep>) {
        steps.forEachIndexed { index, careStep ->
            careStep.order = index
        }
        careStepDao.update(*steps.toTypedArray())
    }
}
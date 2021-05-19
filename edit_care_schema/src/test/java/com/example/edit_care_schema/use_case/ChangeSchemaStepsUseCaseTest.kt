package com.example.edit_care_schema.use_case

import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import com.example.core.gateway.CareSchemaRepo
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ChangeSchemaStepsUseCaseTest {
    private val careSchemaRepo: CareSchemaRepo = mockk()

    private val useCase by lazy {
        ChangeSchemaStepsUseCase(careSchemaRepo)
    }

    @Test
    fun invoke_UpdateCareSchemaInRepo() = runBlocking {
        val careSchemaFromRepo = CareSchema(
            id = 1,
            name = "OMO",
            steps = listOf(
                CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 0),
                CareSchemaStep(type = CareStep.Type.SHAMPOO, order = 1),
                CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 2)
            )
        )
        every { careSchemaRepo.findById(careSchemaFromRepo.id) } returns flowOf(careSchemaFromRepo)
        coJustRun { careSchemaRepo.update(any()) }
        val careSchemaId = 1
        val newSteps = listOf(
            CareSchemaStep(type = CareStep.Type.SHAMPOO, order = 0),
            CareSchemaStep(type = CareStep.Type.OIL, order = 1),
        )

        useCase(careSchemaId, newSteps)

        coVerify {
            careSchemaRepo.update(
                CareSchema(
                    id = careSchemaFromRepo.id,
                    name = careSchemaFromRepo.name,
                    steps = newSteps
                )
            )
        }
    }
}
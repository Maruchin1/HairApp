package com.example.edit_care_schema.use_case

import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import com.example.core.gateway.CareSchemaRepo
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AddSchemaStepUseCaseTest {
    private val careSchemaRepo: CareSchemaRepo = mockk()

    private val addSchemaStep by lazy {
        AddSchemaStepUseCase(careSchemaRepo)
    }

    @Test
    fun invoke_UpdateCareSchemaInRepo() = runBlocking {
        val schemaFromRepo = CareSchema(
            id = 0,
            name = "OMO",
            steps = listOf(
                CareSchemaStep(id = -1, type = CareStep.Type.CONDITIONER, order = 0),
                CareSchemaStep(id = -1, type = CareStep.Type.SHAMPOO, order = 1),
                CareSchemaStep(id = -1, type = CareStep.Type.CONDITIONER, 2)
            )
        )
        val careSchemaId = 0
        val type = CareStep.Type.OIL
        every { careSchemaRepo.findById(schemaFromRepo.id) } returns flowOf(schemaFromRepo)
        coJustRun { careSchemaRepo.update(any()) }

        addSchemaStep(careSchemaId, type)

        coVerify {
            careSchemaRepo.update(
                CareSchema(
                    id = schemaFromRepo.id,
                    name = schemaFromRepo.name,
                    steps = schemaFromRepo.steps + CareSchemaStep(id = -1, type, order = 3)
                )
            )
        }
    }
}
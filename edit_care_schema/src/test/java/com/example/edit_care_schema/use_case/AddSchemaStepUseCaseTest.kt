package com.example.edit_care_schema.use_case

import com.example.common.repository.CareSchemaRepo
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import com.example.edit_care_schema.createOmoCareSchema
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
        val schemaFromRepo = createOmoCareSchema()
        val type = CareStep.Type.OIL
        every { careSchemaRepo.findById(schemaFromRepo.id) } returns flowOf(schemaFromRepo)
        coJustRun { careSchemaRepo.update(any()) }

        addSchemaStep(schemaFromRepo.id, type)

        coVerify {
            careSchemaRepo.update(
                CareSchema(
                    id = schemaFromRepo.id,
                    name = schemaFromRepo.name,
                    steps = schemaFromRepo.steps + CareSchemaStep(-1, type, order = 3)
                )
            )
        }
    }
}
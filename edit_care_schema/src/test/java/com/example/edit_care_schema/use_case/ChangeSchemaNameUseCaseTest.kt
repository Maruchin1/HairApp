package com.example.edit_care_schema.use_case

import com.example.common.repository.CareSchemaRepo
import com.example.edit_care_schema.createOmoCareSchema
import com.example.core.domain.CareSchema
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ChangeSchemaNameUseCaseTest {
    private val careSchemaRepo: CareSchemaRepo = mockk()

    private val useCase by lazy {
        ChangeSchemaNameUseCase(careSchemaRepo)
    }

    @Test
    fun invoke_UpdateSchemaInRepo() = runBlocking {
        val careSchemaFromRepo = createOmoCareSchema()
        val newName = "CG"
        every { careSchemaRepo.findById(any()) } returns flowOf(createOmoCareSchema())
        coJustRun { careSchemaRepo.update(any()) }

        useCase(careSchemaFromRepo.id, newName)

        coVerify {
            careSchemaRepo.update(
                CareSchema(
                    id = careSchemaFromRepo.id,
                    name = newName,
                    steps = careSchemaFromRepo.steps
                )
            )
        }
    }
}
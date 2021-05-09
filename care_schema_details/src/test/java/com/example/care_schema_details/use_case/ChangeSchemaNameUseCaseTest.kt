package com.example.care_schema_details.use_case

import com.example.care_schema_details.createOmoCareSchema
import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo
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
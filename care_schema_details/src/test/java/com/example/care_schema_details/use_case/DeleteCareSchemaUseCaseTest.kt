package com.example.care_schema_details.use_case

import com.example.care_schema_details.createOmoCareSchema
import com.example.core.gateway.CareSchemaRepo
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteCareSchemaUseCaseTest {
    private val careSchemaRepo: CareSchemaRepo = mockk()

    val useCase by lazy {
        DeleteCareSchemaUseCase(careSchemaRepo)
    }

    @Test
    fun invoke_DeleteSchemaFromRepo() = runBlocking {
        val careSchemaFromRepo = createOmoCareSchema()
        every { careSchemaRepo.findById(any()) } returns flowOf(careSchemaFromRepo)
        coJustRun { careSchemaRepo.delete(careSchemaFromRepo) }

        useCase(careSchemaId = careSchemaFromRepo.id)

        coVerify {
            careSchemaRepo.delete(careSchemaFromRepo)
        }
    }
}
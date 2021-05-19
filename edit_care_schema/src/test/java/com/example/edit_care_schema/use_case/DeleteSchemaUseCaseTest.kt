package com.example.edit_care_schema.use_case

import com.example.common.repository.CareSchemaRepo
import com.example.edit_care_schema.createOmoCareSchema
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteSchemaUseCaseTest {
    private val careSchemaRepo: CareSchemaRepo = mockk()

    val useCase by lazy {
        DeleteSchemaUseCase(careSchemaRepo)
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
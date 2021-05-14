package com.example.home.use_case

import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class AddCareSchemaUseCaseTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module {
            single { mockk<CareSchemaRepo>() }
            factory {
                AddCareSchemaUseCase(
                    careSchemaRepo = get()
                )
            }
        })
    }

    private val careSchemaRepo: CareSchemaRepo by inject()
    private val addCareSchemaUseCase: AddCareSchemaUseCase by inject()

    @Test
    fun invoke_AddNewSchemaToRepo() = runBlocking {
        val schemaName = "Własna"
        coJustRun { careSchemaRepo.addNew(any()) }

        addCareSchemaUseCase(schemaName)

        coVerify {
            careSchemaRepo.addNew(
                CareSchema(
                    id = -1,
                    name = schemaName,
                    steps = listOf()
                )
            )
        }
    }
}
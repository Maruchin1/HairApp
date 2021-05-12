package com.example.care_schemas_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.core.domain.CareSchema
import com.example.core.gateway.CareSchemaRepo
import com.example.testing.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class CareSchemasListViewModelTest : KoinTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module {
            single { mockk<CareSchemaRepo>() }
            factory {
                CareSchemasListViewModel(
                    careSchemaRepo = get()
                )
            }
        })
    }

    private val careSchemaRepo: CareSchemaRepo by inject()
    private val viewModel: CareSchemasListViewModel by inject()

    @Test
    fun careSchemas_EmitSchemasFromRepo() = runBlocking {
        val schemasFromRepo = listOf(
            CareSchema(id = 1, name = "OMO", steps = listOf()),
            CareSchema(id = 2, name = "CG", steps = listOf())
        )
        every { careSchemaRepo.findAll() } returns flowOf(schemasFromRepo)

        val result = viewModel.careSchemas.asFlow().first()

        assertThat(result).isEqualTo(schemasFromRepo)
    }
}
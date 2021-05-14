package com.example.home.care_schemas

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class CareSchemasListViewModelTest : KoinTest {

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

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val careSchemaRepo: CareSchemaRepo by inject()
    private val viewModel: CareSchemasListViewModel by inject()

    @Before
    fun before() {
        every { careSchemaRepo.findAll() } returns flowOf(listOf())
    }

    @Test
    fun careSchemas_EmitSchemasFromRepo_SortedAlphabetically() = runBlocking {
        val schemasFromRepo = listOf(
            CareSchema(id = 1, name = "Własna", steps = listOf()),
            CareSchema(id = 2, name = "OMO", steps = listOf()),
            CareSchema(id = 3, name = "CG", steps = listOf()),
        )
        every { careSchemaRepo.findAll() } returns flowOf(schemasFromRepo)

        val result = viewModel.careSchemas.asFlow().first()

        assertThat(result).isEqualTo(listOf(
            schemasFromRepo[2],
            schemasFromRepo[1],
            schemasFromRepo[0]
        ))
    }
}
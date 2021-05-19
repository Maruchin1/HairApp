package com.example.edit_care_schema.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import com.example.core.gateway.CareSchemaRepo
import com.example.edit_care_schema.createOmoCareSchema
import com.example.testing.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EditCareSchemaViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val careSchemaId = 1
    private val careSchemaRepo: CareSchemaRepo = mockk()

    private val viewModel by lazy {
        EditCareSchemaViewModel(
            careSchemaId,
            careSchemaRepo,
        )
    }

    @Before
    fun before() {
        every { careSchemaRepo.findById(any()) } returns flowOf()
    }

    private fun mockCareSchema(): CareSchema {
        val careSchema = createOmoCareSchema()
        every { careSchemaRepo.findById(careSchemaId) } returns flowOf(careSchema)
        return careSchema
    }

    @Test
    fun schemaName_Emits() = runBlocking {
        val careSchema = mockCareSchema()

        val result = viewModel.schemaName.asFlow().first()

        assertThat(result).isEqualTo(careSchema.name)
    }

    @Test
    fun schemaSteps_Emits() = runBlocking {
        val careSchema = mockCareSchema()

        val result = viewModel.schemaSteps.asFlow().first()

        assertThat(result).isEqualTo(careSchema.steps)
    }
}
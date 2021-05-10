package com.example.care_schema_details.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.care_schema_details.use_case.ChangeSchemaNameUseCase
import com.example.care_schema_details.use_case.DeleteCareSchemaUseCase
import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import com.example.core.gateway.CareSchemaRepo
import com.example.testing.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CareSchemaDetailsViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val careSchemaId = 1
    private val careSchemaRepo: CareSchemaRepo = mockk()
    private val changeSchemaNameUseCase: ChangeSchemaNameUseCase = mockk()
    private val deleteCareSchemaUseCase: DeleteCareSchemaUseCase = mockk()

    private val viewModel by lazy {
        CareSchemaDetailsViewModel(
            careSchemaId,
            careSchemaRepo,
            changeSchemaNameUseCase,
            deleteCareSchemaUseCase
        )
    }

    @Before
    fun before() {
        every { careSchemaRepo.findById(any()) } returns flowOf()
    }

    private fun mockCareSchema(): CareSchema {
        val careSchema = CareSchema(
            id = 1,
            name = "OMO",
            steps = listOf(
                CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 1),
                CareSchemaStep(type = CareStep.Type.SHAMPOO, order = 2),
                CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 3)
            )
        )
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

    @Test
    fun changeSchemaName_InvokeUseCase() = runBlocking {
        val careSchema = mockCareSchema()
        val newName = "CG"
        coJustRun { changeSchemaNameUseCase(any(), any()) }

        viewModel.changeSchemaName(newName)

        coVerify { changeSchemaNameUseCase(careSchema.id, newName) }
    }

    @Test
    fun deleteSchema_InvokeUseCase() = runBlocking {
        val careSchema = mockCareSchema()
        coJustRun { deleteCareSchemaUseCase(any()) }

        viewModel.deleteSchema()

        coVerify { deleteCareSchemaUseCase(careSchema.id) }
    }

    @Test
    fun isStepsEditMode_FalseByDefault() = runBlocking {
        val result = viewModel.stepsEditModeEnabled.asFlow().first()

        assertThat(result).isFalse()
    }

    @Test
    fun enableStepsEditMode_StepsEditModeChangesToTrue() = runBlocking {
        val beforeEnabled = viewModel.stepsEditModeEnabled.asFlow().first()
        viewModel.enableStepsEditMode()
        val afterEnabled = viewModel.stepsEditModeEnabled.asFlow().first()

        assertThat(beforeEnabled).isFalse()
        assertThat(afterEnabled).isTrue()
    }

    @Test
    fun saveStepsChanges_StepsEditModeChangesToFalse() = runBlocking {
        viewModel.enableStepsEditMode()
        val beforeSave = viewModel.stepsEditModeEnabled.asFlow().first()
        viewModel.saveStepsChanges()
        val afterSave = viewModel.stepsEditModeEnabled.asFlow().first()

        assertThat(beforeSave).isTrue()
        assertThat(afterSave).isFalse()
    }
}
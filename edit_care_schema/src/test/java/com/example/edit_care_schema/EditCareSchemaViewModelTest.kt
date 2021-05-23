package com.example.edit_care_schema

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.ProductType
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EditCareSchemaViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val careSchemaDao: CareSchemaDao = mockk()
    private val careSchemaStepDao: CareSchemaStepDao = mockk()
    private val viewModel by lazy {
        EditCareSchemaViewModel(careSchemaDao, careSchemaStepDao)
    }

    private val selectedSchema = CareSchemaWithSteps(
        careSchema = CareSchema(id = 1, name = "OMO"),
        steps = listOf(
            CareSchemaStep(
                id = 2,
                prouctType = ProductType.SHAMPOO,
                order = 1,
                careSchemaId = 1
            ),
            CareSchemaStep(
                id = 1,
                prouctType = ProductType.CONDITIONER,
                order = 0,
                careSchemaId = 1
            ),
            CareSchemaStep(
                id = 3,
                prouctType = ProductType.CONDITIONER,
                order = 2,
                careSchemaId = 1
            )
        )
    )

    @Before
    fun before() {
        every { careSchemaDao.getById(1) } returns flowOf(selectedSchema)
    }

    @Test
    fun schemaName_EmitSelectedSchemaName() = runBlocking {
        viewModel.selectSchema(1)
        val result = viewModel.schemaName.asFlow().firstOrNull()

        assertThat(result).isEqualTo("OMO")
    }

    @Test
    fun schemaSteps_EmitSelectedSchemaSteps_SortedByOrder() = runBlocking {
        viewModel.selectSchema(1)
        val result = viewModel.schemaSteps.asFlow().firstOrNull()

        assertThat(result).containsExactly(
            selectedSchema.steps[1],
            selectedSchema.steps[0],
            selectedSchema.steps[2]
        ).inOrder()
    }

    @Test
    fun changeSchemaName_UpdateSelectedSchemaInDb_WithNewName() = runBlocking {
        coJustRun { careSchemaDao.update(*anyVararg()) }

        viewModel.selectSchema(1)
        viewModel.changeSchemaName("Moje OMO")

        coVerify {
            careSchemaDao.update(
                selectedSchema.careSchema.copy(name = "Moje OMO")
            )
        }
    }

    @Test
    fun deleteSchema_DeleteSelectedSchemaFromDb() = runBlocking {
        coJustRun { careSchemaDao.delete(*anyVararg()) }

        viewModel.selectSchema(1)
        viewModel.deleteSchema()

        coVerify {
            careSchemaDao.delete(selectedSchema.careSchema)
        }
    }

    @Test
    fun addStep_InsertNewStepToDb_WithGivenType_WithLastOrder_ForSelectedSchema() = runBlocking {
        coJustRun { careSchemaStepDao.insert(*anyVararg()) }

        viewModel.selectSchema(1)
        viewModel.addStep(ProductType.OIL)

        coVerify {
            careSchemaStepDao.insert(
                CareSchemaStep(
                    id = 0,
                    prouctType = ProductType.OIL,
                    order = 3,
                    careSchemaId = 1
                )
            )
        }
    }

    @Test
    fun updateSteps_UpdateGivenStepsInDb() = runBlocking {
        val stepsToUpdate = listOf(
            selectedSchema.steps[0].copy(order = 2),
            selectedSchema.steps[2].copy(order = 0)
        )
        coJustRun { careSchemaStepDao.update(*anyVararg()) }

        viewModel.selectSchema(1)
        viewModel.updateSteps(stepsToUpdate)

        coVerify {
            careSchemaStepDao.update(*stepsToUpdate.toTypedArray())
        }
    }

    @Test
    fun deleteStep_DeleteStepFromDb() = runBlocking {
        val stepToDelete = selectedSchema.steps[1]
        coJustRun { careSchemaStepDao.delete(*anyVararg()) }
        coJustRun { careSchemaStepDao.update(*anyVararg()) }

        viewModel.selectSchema(1)
        viewModel.deleteStep(stepToDelete)

        coVerify {
            careSchemaStepDao.delete(stepToDelete)
        }
    }

    @Test
    fun deleteStep_UpdateStepsOrderInDb() = runBlocking {
        val stepToDelete = selectedSchema.steps[1]
        coJustRun { careSchemaStepDao.delete(*anyVararg()) }
        coJustRun { careSchemaStepDao.update(*anyVararg()) }

        viewModel.selectSchema(1)
        viewModel.deleteStep(stepToDelete)

        coVerify {
            careSchemaStepDao.update(
                selectedSchema.steps[0].copy(order = 0),
                selectedSchema.steps[2].copy(order = 1)
            )
        }
    }
}
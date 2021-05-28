package com.example.edit_care_schema.use_case

import android.content.Context
import arrow.core.getOrHandle
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.Product
import com.example.corev2.relations.CareSchemaWithSteps
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteSchemaStepUseCaseTest {
    private val actions: DeleteSchemaStepUseCase.Actions = mockk()
    private val careSchemaStepDao: CareSchemaStepDao = mockk()
    private val updateStepsOrderUseCase: UpdateStepsOrderUseCase = mockk()
    private val context: Context = mockk()
    private val careSchemaWithSteps = CareSchemaWithSteps(
        careSchema = CareSchema(id = 1, name = "OMO"),
        steps = listOf(
            CareSchemaStep(
                id = 1,
                productType = Product.Type.CONDITIONER,
                order = 0,
                careSchemaId = 1
            ),
            CareSchemaStep(
                id = 2,
                productType = Product.Type.SHAMPOO,
                order = 1,
                careSchemaId = 1
            ),
            CareSchemaStep(
                id = 3,
                productType = Product.Type.CONDITIONER,
                order = 2,
                careSchemaId = 1
            ),
        )
    )
    private val stepToDelete = CareSchemaStep(
        id = 3,
        productType = Product.Type.CONDITIONER,
        order = 2,
        careSchemaId = 1
    )
    private val deleteSchemaStepUseCase by lazy {
        DeleteSchemaStepUseCase(actions, careSchemaStepDao, updateStepsOrderUseCase)
    }

    @Before
    fun before() {
        coJustRun { careSchemaStepDao.delete(*anyVararg()) }
        coJustRun { updateStepsOrderUseCase(any()) }
        coEvery { actions.askForConfirmation(any(), any()) } returns true
    }

    @Test
    fun careSchemaWithStepsIsNull() = runBlocking {
        val result = deleteSchemaStepUseCase(context, null, stepToDelete)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(DeleteSchemaStepUseCase.Fail.NoSchema::class.java)
        }
        coVerify(exactly = 0) {
            careSchemaStepDao.delete(*anyVararg())
            updateStepsOrderUseCase(any())
        }
    }

    @Test
    fun deletionNotConfirmed() = runBlocking {
        coEvery { actions.askForConfirmation(any(), any()) } returns false

        val result = deleteSchemaStepUseCase(context, careSchemaWithSteps, stepToDelete)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(DeleteSchemaStepUseCase.Fail.DeletionNotConfirmed::class.java)
        }
        coVerify(exactly = 0) {
            careSchemaStepDao.delete(*anyVararg())
            updateStepsOrderUseCase(any())
        }
    }

    @Test
    fun deletionConfirmed() = runBlocking {
        coEvery { actions.askForConfirmation(any(), any()) } returns true

        val result = deleteSchemaStepUseCase(context, careSchemaWithSteps, stepToDelete)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careSchemaStepDao.delete(stepToDelete)
            updateStepsOrderUseCase(
                listOf(
                    careSchemaWithSteps.steps[0],
                    careSchemaWithSteps.steps[1]
                )
            )
        }
    }
}
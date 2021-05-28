package com.example.edit_care_schema.use_case

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

class AddSchemaStepUseCaseTest {
    private val actions: AddSchemaStepUseCase.Actions = mockk()
    private val careSchemaStepDao: CareSchemaStepDao = mockk()
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
    private val addSchemaStepUseCase by lazy {
        AddSchemaStepUseCase(actions, careSchemaStepDao)
    }

    @Before
    fun before() {
        coJustRun { careSchemaStepDao.insert(*anyVararg()) }
    }

    @Test
    fun careSchemaIsNull() = runBlocking {
        coEvery { actions.askForProductType(any()) } returns Product.Type.OIL

        val result = addSchemaStepUseCase(mockk(), null)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(AddSchemaStepUseCase.Fail.NoCareSchema::class.java)
        }
        coVerify(exactly = 0) {
            careSchemaStepDao.insert(*anyVararg())
        }
    }

    @Test
    fun productTypeNotSelected() = runBlocking {
        coEvery { actions.askForProductType(any()) } returns null

        val result = addSchemaStepUseCase(mockk(), careSchemaWithSteps)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(AddSchemaStepUseCase.Fail.ProductTypeNotSelected::class.java)
        }
        coVerify(exactly = 0) {
            careSchemaStepDao.insert(*anyVararg())
        }
    }

    @Test
    fun successfullyAddedStep() = runBlocking {
        coEvery { actions.askForProductType(any()) } returns Product.Type.OIL

        val result = addSchemaStepUseCase(mockk(), careSchemaWithSteps)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careSchemaStepDao.insert(
                CareSchemaStep(
                    id = 0,
                    productType = Product.Type.OIL,
                    order = 3,
                    careSchemaId = 1
                )
            )
        }
    }
}
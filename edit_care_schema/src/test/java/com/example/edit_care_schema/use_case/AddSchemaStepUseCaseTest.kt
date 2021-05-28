package com.example.edit_care_schema.use_case

import arrow.core.getOrHandle
import com.example.corev2.dao.CareSchemaStepDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.Product
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.corev2.ui.DialogService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddSchemaStepUseCaseTest {
    private val dialogService: DialogService = mockk()
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
        AddSchemaStepUseCase(dialogService, careSchemaStepDao)
    }

    @Before
    fun before() {
        coJustRun { careSchemaStepDao.insert(*anyVararg()) }
    }

    @Test
    fun whenCareSchemaIsNull_ReturnNoCareSchemaFail() = runBlocking {
        coEvery { dialogService.selectProductType(any()) } returns Product.Type.OIL

        val result = addSchemaStepUseCase(mockk(), null)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(AddSchemaStepUseCase.Fail.NoCareSchema::class.java)
        }
    }

    @Test
    fun whenCareSchemaIsNull_DoNotInsertNewStepToDb() = runBlocking {
        coEvery { dialogService.selectProductType(any()) } returns Product.Type.OIL

        addSchemaStepUseCase(mockk(), null)

        coVerify(exactly = 0) {
            careSchemaStepDao.insert(*anyVararg())
        }
    }

    @Test
    fun whenProductTypeNotSelected_ReturnProductNotSelectedFail() = runBlocking {
        coEvery { dialogService.selectProductType(any()) } returns null

        val result = addSchemaStepUseCase(mockk(), careSchemaWithSteps)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(AddSchemaStepUseCase.Fail.ProductTypeNotSelected::class.java)
        }
    }

    @Test
    fun whenProductTypeNotSelected_DoNotInsertNewStepToDb() = runBlocking {
        coEvery { dialogService.selectProductType(any()) } returns null

        addSchemaStepUseCase(mockk(), careSchemaWithSteps)

        coVerify(exactly = 0) {
            careSchemaStepDao.insert(*anyVararg())
        }
    }

    @Test
    fun whenProductTypeSelected_ReturnRight() = runBlocking {
        coEvery { dialogService.selectProductType(any()) } returns Product.Type.OIL

        val result = addSchemaStepUseCase(mockk(), careSchemaWithSteps)

        assertThat(result.isRight()).isTrue()
    }

    @Test
    fun whenProductTypeSelected_InsertNewStepToDb() = runBlocking {
        coEvery { dialogService.selectProductType(any()) } returns Product.Type.OIL

        addSchemaStepUseCase(mockk(), careSchemaWithSteps)

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
package com.example.care_details.use_case

import arrow.core.handleError
import com.example.corev2.dao.CareStepDao
import com.example.corev2.entities.CareStep
import com.example.corev2.entities.Product
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SelectProductForStepUseCaseTest {
    private val actions: SelectProductForStepUseCase.Actions = mockk()
    private val careStepDao: CareStepDao = mockk()
    private val selectProductForStepUseCase by lazy {
        SelectProductForStepUseCase(actions, careStepDao)
    }

    private val careStepToUpdate = CareStep(
        id = 1,
        productType = Product.Type.CONDITIONER,
        order = 0,
        productId = null,
        careId = 1
    )
    private val selectedProductId = 2L

    @Before
    fun before() {
        coEvery { actions.askForProductId(any()) } returns selectedProductId
        coJustRun { careStepDao.update(*anyVararg()) }
    }

    @Test
    fun productNotSelected() = runBlocking {
        coEvery { actions.askForProductId(any()) } returns null

        val result = selectProductForStepUseCase(careStepToUpdate)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(SelectProductForStepUseCase.Fail.ProductNotSelected::class.java)
        }
        coVerify(exactly = 0) {
            careStepDao.update(*anyVararg())
        }
    }

    @Test
    fun successfullySelectedProduct() = runBlocking {
        val result = selectProductForStepUseCase(careStepToUpdate)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careStepDao.update(careStepToUpdate.copy(productId = selectedProductId))
        }
    }
}
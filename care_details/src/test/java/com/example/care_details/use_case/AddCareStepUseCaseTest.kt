package com.example.care_details.use_case

import arrow.core.handleError
import com.example.corev2.dao.CareStepDao
import com.example.corev2.entities.Care
import com.example.corev2.entities.CareStep
import com.example.corev2.entities.Product
import com.example.corev2.relations.CareStepWithProduct
import com.example.corev2.relations.CareWithStepsAndPhotos
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class AddCareStepUseCaseTest {
    private val actions = mockk<AddCareStepUseCase.Actions>()
    private val careStepDao = mockk<CareStepDao>()
    private val addCareStepUseCase by lazy {
        AddCareStepUseCase(actions, careStepDao)
    }

    private val careToAddStep = CareWithStepsAndPhotos(
        care = Care(
            id = 1,
            schemaName = "OMO",
            date = LocalDateTime.now(),
            notes = "Lorem ipsum"
        ),
        steps = listOf(
            CareStepWithProduct(
                careStep = CareStep(
                    id = 1,
                    productType = Product.Type.CONDITIONER,
                    order = 0,
                    productId = null,
                    careId = 1
                ),
                product = null
            ),
            CareStepWithProduct(
                careStep = CareStep(
                    id = 2,
                    productType = Product.Type.SHAMPOO,
                    order = 1,
                    productId = null,
                    careId = 1
                ),
                product = null
            ),
            CareStepWithProduct(
                careStep = CareStep(
                    id = 3,
                    productType = Product.Type.CONDITIONER,
                    order = 2,
                    productId = null,
                    careId = 1
                ),
                product = null
            ),
        ),
        photos = listOf()
    )

    @Before
    fun before() {
        coEvery { actions.askForProductId() } returns 11
        coJustRun { careStepDao.insert(*anyVararg()) }
    }

    @Test
    fun noCare() = runBlocking {
        val result = addCareStepUseCase(null)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(AddCareStepUseCase.Fail.NoCare::class.java)
        }
        coVerify(exactly = 0) {
            careStepDao.insert(*anyVararg())
        }
    }

    @Test
    fun productNotSelected() = runBlocking {
        coEvery { actions.askForProductId() } returns null

        val result = addCareStepUseCase(careToAddStep)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(AddCareStepUseCase.Fail.ProductNotSelected::class.java)
        }
        coVerify(exactly = 0) {
            careStepDao.insert(*anyVararg())
        }
    }

    @Test
    fun successfullyAddCareStep() = runBlocking {
        val result = addCareStepUseCase(careToAddStep)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careStepDao.insert(
                CareStep(
                    productType = null,
                    order = 3,
                    productId = 11,
                    careId = 1
                )
            )
        }
    }
}
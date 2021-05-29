package com.example.cares_list.use_case

import android.app.Activity
import arrow.core.getOrElse
import arrow.core.getOrHandle
import arrow.core.handleError
import com.example.corev2.dao.CareDao
import com.example.corev2.dao.CareStepDao
import com.example.corev2.entities.*
import com.example.corev2.navigation.CareDetailsDestination
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.corev2.service.ClockService
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class AddNewCareUseCaseTest {
    private val actions: AddNewCareUseCase.Actions = mockk()
    private val clockService: ClockService = mockk()
    private val careDao: CareDao = mockk()
    private val careStepDao: CareStepDao = mockk()
    private val addNewCareUseCase by lazy {
        AddNewCareUseCase(actions, clockService, careDao, careStepDao)
    }

    private val activity: Activity = mockk()
    private val selectedCareSchemaWithSteps = CareSchemaWithSteps(
        careSchema = CareSchema(id = 1, name = "OMO"),
        steps = listOf(
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
            CareSchemaStep(
                id = 1,
                productType = Product.Type.CONDITIONER,
                order = 0,
                careSchemaId = 1
            ),
        )
    )
    private val today = LocalDate.now()

    @Before
    fun before() {
        coEvery { actions.selectCareSchema(any()) } returns selectedCareSchemaWithSteps
        every { clockService.getNow() } returns today
        coEvery { careDao.insert(*anyVararg()) } returns arrayOf(1)
        coJustRun { careStepDao.insert(*anyVararg()) }
    }

    @Test
    fun careSchemaNotSelected() = runBlocking {
        coEvery { actions.selectCareSchema(any()) } returns null

        val result = addNewCareUseCase(activity)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(AddNewCareUseCase.Fail.CareSchemaNotSelected::class.java)
        }
        Unit
    }

    @Test
    fun successfullyAddedCareToDb() = runBlocking {
        val result = addNewCareUseCase(activity)

        assertThat(result.isRight()).isTrue()
        assertThat(result.getOrElse { -1 }).isEqualTo(1)
        coVerify {
            careDao.insert(
                Care(schemaName = "OMO", date = today, notes = "")
            )
        }
    }

    @Test
    fun successfullyAddedCareStepsToDb() = runBlocking {
        addNewCareUseCase(activity)

        coVerify {
            careStepDao.insert(
                CareStep(
                    productType = Product.Type.SHAMPOO,
                    order = 1,
                    productId = null,
                    careId = 1
                ),
                CareStep(
                    productType = Product.Type.CONDITIONER,
                    order = 2,
                    productId = null,
                    careId = 1
                ),
                CareStep(
                    productType = Product.Type.CONDITIONER,
                    order = 0,
                    productId = null,
                    careId = 1
                )
            )
        }
    }
}
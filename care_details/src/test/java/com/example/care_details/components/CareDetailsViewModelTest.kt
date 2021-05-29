package com.example.care_details.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.CareStep
import com.example.corev2.entities.Product
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.corev2.service.ClockService
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class CareDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val clockService: ClockService = mockk()
    private val careSchemaDao: CareSchemaDao = mockk()
    private val viewModel by lazy {
        CareDetailsViewModel(clockService, careSchemaDao)
    }

    @Before
    fun before() {
        every { clockService.getNow() } returns LocalDate.now()
    }

    @Test
    fun careDate_IsCurrentDateByDefault() = runBlocking {
        val fakeNow = LocalDate.now()
        every { clockService.getNow() } returns fakeNow

        val result = viewModel.careDate.asFlow().firstOrNull()

        assertThat(result).isEqualTo(fakeNow)
    }

    @Test
    fun onCareDateSelected_ChangeCareDate() = runBlocking {
        val newDate = LocalDate.now()

        viewModel.onCareDateSelected(newDate)
        val result = viewModel.careDate.asFlow().firstOrNull()

        assertThat(result).isEqualTo(newDate)
    }

    @Test
    fun onSchemaSelected_ChangeSchemaName_AndApplyStepsInCorrectOrder() = runBlocking {
        val schemaWithStepsFromDb = CareSchemaWithSteps(
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
        every { careSchemaDao.getById(any()) } returns flowOf(schemaWithStepsFromDb)

        viewModel.onSchemaSelected(1)
        val schemaName = viewModel.schemaName.asFlow().firstOrNull()
        val careSteps = viewModel.careSteps.asFlow().firstOrNull()

        assertThat(schemaName).isEqualTo(schemaWithStepsFromDb.careSchema.name)
//        assertThat(careSteps).containsExactly(
//            CareStep(
//                id = -1,
//                productType = Product.Type.CONDITIONER,
//                order = 0,
//                productId = null,
//                careId = -1
//            ),
//            CareStep(
//                id = -1,
//                prod
//            )
//        )
    }
}
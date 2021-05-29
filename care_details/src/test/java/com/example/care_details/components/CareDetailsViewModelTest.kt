package com.example.care_details.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.corev2.dao.CareDao
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.*
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.corev2.relations.CareStepWithProduct
import com.example.corev2.relations.CareWithStepsAndPhotos
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
    private val careDao: CareDao = mockk()
    private val viewModel by lazy {
        CareDetailsViewModel(clockService, careDao)
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
    fun onCareSelected_ApplyCareData() = runBlocking {
        val careWithStepsAndPhotosFromDb = CareWithStepsAndPhotos(
            care = Care(
                id = 1,
                schemaName = "OMO",
                date = LocalDate.now(),
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
                        order = 0,
                        productId = null,
                        careId = 1
                    ),
                    product = null
                ),
                CareStepWithProduct(
                    careStep = CareStep(
                        id = 3,
                        productType = Product.Type.CONDITIONER,
                        order = 0,
                        productId = null,
                        careId = 1
                    ),
                    product = null
                )
            ),
            photos = listOf(
                CarePhoto(id = 1, data = "abc", careId = 1)
            )
        )
        every { careDao.getById(any()) } returns flowOf(careWithStepsAndPhotosFromDb)

        viewModel.onCareSelected(1)
        val schemaName = viewModel.schemaName.asFlow().firstOrNull()
        val steps = viewModel.steps.asFlow().firstOrNull()
        val notes = viewModel.notes.asFlow().firstOrNull()
        val photos = viewModel.photos.asFlow().firstOrNull()

        assertThat(schemaName).isEqualTo(careWithStepsAndPhotosFromDb.care.schemaName)
        assertThat(steps).isEqualTo(careWithStepsAndPhotosFromDb.steps)
        assertThat(notes).isEqualTo(careWithStepsAndPhotosFromDb.care.notes)
        assertThat(photos).isEqualTo(careWithStepsAndPhotosFromDb.photos)
    }
}
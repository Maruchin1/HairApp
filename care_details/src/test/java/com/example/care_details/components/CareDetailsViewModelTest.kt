package com.example.care_details.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import arrow.core.Either
import com.example.care_details.use_case.ChangeCareDateUseCase
import com.example.care_details.use_case.DeleteCareUseCase
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.*
import com.example.corev2.relations.CareStepWithProduct
import com.example.corev2.relations.CareWithStepsAndPhotos
import com.example.corev2.service.ClockService
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class CareDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val clockService: ClockService = mockk()
    private val careDao: CareDao = mockk()
    private val changeCareDateUseCase: ChangeCareDateUseCase = mockk()
    private val deleteCareUseCase: DeleteCareUseCase = mockk()
    private val viewModel by lazy {
        CareDetailsViewModel(clockService, careDao, changeCareDateUseCase, deleteCareUseCase)
    }

    private val careWithStepsAndPhotosFromDb = CareWithStepsAndPhotos(
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

    @Before
    fun before() {
        every { clockService.getNow() } returns LocalDateTime.now()
        every { careDao.getById(1) } returns flowOf(careWithStepsAndPhotosFromDb)
    }

    @Test
    fun careDate_IsCurrentDateByDefault() = runBlocking {
        val fakeNow = LocalDateTime.now()
        every { clockService.getNow() } returns fakeNow

        val result = viewModel.careDate.asFlow().firstOrNull()

        assertThat(result).isEqualTo(fakeNow)
    }

    @Test
    fun onCareSelected_ApplyCareData() = runBlocking {
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

    @Test
    fun onChangeDateClicked_InvokeChangeCareDateUseCase() = runBlocking {
        coEvery { changeCareDateUseCase(any()) } returns Either.Right(Unit)

        viewModel.onCareSelected(1)
        viewModel.onChangeDateClicked()

        coVerify {
            changeCareDateUseCase(careWithStepsAndPhotosFromDb.care)
        }
    }

    @Test
    fun onDeleteCareClicked_InvokeDeleteCareUseCase() = runBlocking {
        coEvery { deleteCareUseCase(any()) } returns Either.Right(Unit)

        viewModel.onCareSelected(1)
        viewModel.onDeleteCareClicked()

        coVerify {
            deleteCareUseCase(careWithStepsAndPhotosFromDb.care)
        }
    }
}
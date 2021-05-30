package com.example.care_details.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import arrow.core.Either
import com.example.care_details.use_case.ChangeCareDateUseCase
import com.example.care_details.use_case.ChangeCareNotesUseCase
import com.example.care_details.use_case.DeleteCareUseCase
import com.example.care_details.use_case.SelectProductForStepUseCase
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

    private val careDao: CareDao = mockk()
    private val changeCareDateUseCase: ChangeCareDateUseCase = mockk()
    private val deleteCareUseCase: DeleteCareUseCase = mockk()
    private val selectProductForStepUseCase: SelectProductForStepUseCase = mockk()
    private val changeCareNotesUseCase: ChangeCareNotesUseCase = mockk()
    private val viewModel by lazy {
        CareDetailsViewModel(
            careDao,
            changeCareDateUseCase,
            deleteCareUseCase,
            selectProductForStepUseCase,
            changeCareNotesUseCase
        )
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
        every { careDao.getById(1) } returns flowOf(careWithStepsAndPhotosFromDb)
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

    @Test
    fun onCareStepClicked_InvokeSelectProductForStepUseCase() = runBlocking {
        coEvery { selectProductForStepUseCase(any()) } returns Either.Right(Unit)
        val clickedStep = careWithStepsAndPhotosFromDb.steps[0].careStep

        viewModel.onCareStepClicked(clickedStep)

        coVerify {
            selectProductForStepUseCase(clickedStep)
        }
    }

    @Test
    fun onEditNotesClicked_ChangeNotesEditModeToTrue() = runBlocking {
        val editModeBefore = viewModel.notesEditMode.asFlow().firstOrNull()
        viewModel.onEditNotesClicked()
        val editModeAfter = viewModel.notesEditMode.asFlow().firstOrNull()

        assertThat(editModeBefore).isFalse()
        assertThat(editModeAfter).isTrue()
    }

    @Test
    fun onCancelNotesEditionClicked_ChangeNotesEditModeToFalse() = runBlocking {
        viewModel.onEditNotesClicked()
        val editModeBefore = viewModel.notesEditMode.asFlow().firstOrNull()
        viewModel.onCancelNotesEditionClicked()
        val editModeAfter = viewModel.notesEditMode.asFlow().firstOrNull()

        assertThat(editModeBefore).isTrue()
        assertThat(editModeAfter).isFalse()
    }

    @Test
    fun onSaveNotesClicked_CallChangeCareNotesUseCase() = runBlocking {
        val newNotes = "Lorem ipsum"
        coEvery { changeCareNotesUseCase(any(), any()) } returns Either.Right(Unit)

        viewModel.onCareSelected(1)
        val result = viewModel.onSaveNotesClicked(newNotes)

        assertThat(result.isRight()).isTrue()
        coVerify {
            changeCareNotesUseCase(careWithStepsAndPhotosFromDb.care, newNotes)
        }
    }
}
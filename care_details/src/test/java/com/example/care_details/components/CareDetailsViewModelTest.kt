package com.example.care_details.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import arrow.core.Either
import com.example.care_details.use_case.*
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

    private val careDao = mockk<CareDao>()
    private val changeCareDateUseCase = mockk<ChangeCareDateUseCase>()
    private val deleteCareUseCase = mockk<DeleteCareUseCase>()
    private val selectProductForStepUseCase = mockk<SelectProductForStepUseCase>()
    private val deleteCareStepUseCase = mockk<DeleteCareStepUseCase>()
    private val addCareStepUseCase = mockk<AddCareStepUseCase>()
    private val updateCareStepsOrderUseCase = mockk<UpdateCareStepsOrderUseCase>()
    private val addCarePhotoUseCase = mockk<AddCarePhotoUseCase>()
    private val changeCareNotesUseCase = mockk<ChangeCareNotesUseCase>()
    private val viewModel by lazy {
        CareDetailsViewModel(
            careDao,
            changeCareDateUseCase,
            deleteCareUseCase,
            selectProductForStepUseCase,
            deleteCareStepUseCase,
            addCareStepUseCase,
            updateCareStepsOrderUseCase,
            addCarePhotoUseCase,
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

        viewModel.onCareSelected(1)
        viewModel.onCareStepClicked(clickedStep)

        coVerify {
            selectProductForStepUseCase(clickedStep)
        }
    }

    @Test
    fun onCareStepLongClicked_InvokeDeleteCareStepUseCase() = runBlocking {
        coEvery { deleteCareStepUseCase(any()) } returns Either.Right(Unit)
        val clickedStep = careWithStepsAndPhotosFromDb.steps[0].careStep

        viewModel.onCareSelected(1)
        viewModel.onCareStepLongClicked(clickedStep)

        coVerify {
            deleteCareStepUseCase(clickedStep)
        }
    }

    @Test
    fun onAddNewCareStepClicked_InvokeAddCareStepUseCase() = runBlocking {
        coEvery { addCareStepUseCase(any()) } returns Either.Right(Unit)

        viewModel.onCareSelected(1)
        viewModel.onAddNewCareStepClicked()

        coVerify {
            addCareStepUseCase(careWithStepsAndPhotosFromDb)
        }
    }

    @Test
    fun onCareStepsOrderChanged_InvokeUpdateCareStepsOrderUseCase() = runBlocking {
        coJustRun { updateCareStepsOrderUseCase(any()) }
        val steps = listOf(
            CareStep(
                id = 2,
                productType = Product.Type.SHAMPOO,
                order = 2,
                productId = null,
                careId = 1
            ),
            CareStep(
                id = 1,
                productType = Product.Type.CONDITIONER,
                order = 1,
                productId = null,
                careId = 1
            ),
        )

        viewModel.onCareSelected(1)
        viewModel.onCareStepsOrderChanged(steps)

        coVerify {
            updateCareStepsOrderUseCase(steps)
        }
    }

    @Test
    fun onAddNewPhotoClicked_InvokeAddCarePhotoUseCase() = runBlocking {
        coEvery { addCarePhotoUseCase(any()) } returns Either.Right(Unit)

        viewModel.onCareSelected(1)
        viewModel.onAddNewPhotoClicked()

        coVerify {
            addCarePhotoUseCase(careWithStepsAndPhotosFromDb.care)
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
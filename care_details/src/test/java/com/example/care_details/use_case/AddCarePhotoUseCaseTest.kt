package com.example.care_details.use_case

import arrow.core.handleError
import com.example.corev2.dao.CarePhotoDao
import com.example.corev2.entities.Care
import com.example.corev2.entities.CarePhoto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class AddCarePhotoUseCaseTest {
    private val actions = mockk<AddCarePhotoUseCase.Actions>()
    private val carePhotoDao = mockk<CarePhotoDao>()
    private val addCareNotesUseCase by lazy {
        AddCarePhotoUseCase(actions, carePhotoDao)
    }

    private val careToAdd = Care(
        id = 1,
        schemaName = "OMO",
        date = LocalDateTime.now(),
        notes = "Lorem ipsum"
    )
    private val capturedPhotoData = "abc"

    @Before
    fun before() {
        coEvery { actions.captureNewCarePhoto() } returns capturedPhotoData
        coJustRun { carePhotoDao.insert(*anyVararg()) }
    }

    @Test
    fun noCare() = runBlocking {
        val result = addCareNotesUseCase(null)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(AddCarePhotoUseCase.Fail.NoCare::class.java)
        }
        coVerify(exactly = 0) {
            carePhotoDao.insert(*anyVararg())
        }
    }

    @Test
    fun photoNotCaptured() = runBlocking {
        coEvery { actions.captureNewCarePhoto() } returns null

        val result = addCareNotesUseCase(careToAdd)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(AddCarePhotoUseCase.Fail.PhotoNotCaptured::class.java)
        }
        coVerify(exactly = 0) {
            carePhotoDao.insert(*anyVararg())
        }
    }

    @Test
    fun successfullyAddedCare() = runBlocking {
        val result = addCareNotesUseCase(careToAdd)

        assertThat(result.isRight()).isTrue()
        coVerify {
            carePhotoDao.insert(CarePhoto(data = capturedPhotoData, careId = careToAdd.id))
        }
    }
}
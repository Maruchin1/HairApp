package com.example.care_details.use_case

import arrow.core.handleError
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class DeleteCareUseCaseTest {
    private val actions: DeleteCareUseCase.Actions = mockk()
    private val careDao: CareDao = mockk()
    private val deleteCareUseCase by lazy {
        DeleteCareUseCase(actions, careDao)
    }

    private val care = Care(
        id = 1,
        schemaName = "OMO",
        date = LocalDateTime.of(2021, 5, 16, 0, 0),
        notes = ""
    )

    @Before
    fun before() {
        coEvery { actions.confirmCareDeletion() } returns true
        coJustRun { careDao.delete(*anyVararg()) }
    }

    @Test
    fun noCare() = runBlocking {
        val result = deleteCareUseCase(null)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(DeleteCareUseCase.Fail.NoCare::class.java)
        }
        coVerify(exactly = 0) {
            careDao.delete(*anyVararg())
        }
    }

    @Test
    fun deletionNotConfirmed() = runBlocking {
        coEvery { actions.confirmCareDeletion() } returns false

        val result = deleteCareUseCase(care)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(DeleteCareUseCase.Fail.DeletionNotConfirmed::class.java)
        }
        coVerify(exactly = 0) {
            careDao.delete(*anyVararg())
        }
    }

    @Test
    fun successfullyDeleted() = runBlocking {
        val result = deleteCareUseCase(care)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careDao.delete(care)
        }
    }
}
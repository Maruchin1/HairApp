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

class ChangeCareDateUseCaseTest {
    private val actions: ChangeCareDateUseCase.Actions = mockk()
    private val careDao: CareDao = mockk()
    private val changeCareDateUseCase by lazy {
        ChangeCareDateUseCase(actions, careDao)
    }

    private val selectedNewDate = LocalDateTime.of(2021, 5, 20, 0, 0)
    private val care = Care(
        id = 1,
        schemaName = "OMO",
        date = LocalDateTime.of(2021, 5, 16, 0, 0),
        notes = ""
    )

    @Before
    fun before() {
        coEvery { actions.askForNewDate(any()) } returns selectedNewDate
        coJustRun { careDao.update(*anyVararg()) }
    }

    @Test
    fun newDateNotSelected() = runBlocking {
        coEvery { actions.askForNewDate(any()) } returns null

        val result = changeCareDateUseCase(care)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(ChangeCareDateUseCase.Fail.NewDateNotSelected::class.java)
        }
        coVerify(exactly = 0) {
            careDao.update(*anyVararg())
        }
    }

    @Test
    fun successfullyChangedDate() = runBlocking {
        val result = changeCareDateUseCase(care)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careDao.update(care.copy(date = selectedNewDate))
        }
    }
}
package com.example.care_details.use_case

import arrow.core.handleError
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care
import com.google.common.truth.Truth.assertThat
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class ChangeCareNotesTest {
    private val careDao: CareDao = mockk()
    private val changeCareNotesUseCase by lazy {
        ChangeCareNotesUseCase(careDao)
    }

    private val careToUpdate = Care(
        id = 1,
        schemaName = "OMO",
        date = LocalDateTime.of(2021, 5, 16, 0, 0),
        notes = "Lorem ipsum"
    )
    private val newNotes = "ABC"

    @Before
    fun before() {
        coJustRun { careDao.update(*anyVararg()) }
    }

    @Test
    fun noCare() = runBlocking {
        val result = changeCareNotesUseCase(null, newNotes)

        assertThat(result.isLeft()).isTrue()
        result.handleError {
            assertThat(it).isInstanceOf(ChangeCareNotesUseCase.Fail.NoCare::class.java)
        }
        coVerify(exactly = 0) {
            careDao.update(*anyVararg())
        }
    }

    @Test
    fun successfullyChangedNoted() = runBlocking {
        val result = changeCareNotesUseCase(careToUpdate, newNotes)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careDao.update(*anyVararg())
        }
    }
}
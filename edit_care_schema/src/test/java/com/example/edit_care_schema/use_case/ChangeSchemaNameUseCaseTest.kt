package com.example.edit_care_schema.use_case

import arrow.core.getOrHandle
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ChangeSchemaNameUseCaseTest {
    private val actions: ChangeSchemaNameUseCase.Actions = mockk()
    private val careSchemaDao: CareSchemaDao = mockk()
    private val careSchema = CareSchema(id = 1, name = "OMO")
    private val changeSchemaNameUseCase by lazy {
        ChangeSchemaNameUseCase(actions, careSchemaDao)
    }

    @Before
    fun before() {
        coJustRun { careSchemaDao.update(*anyVararg()) }
    }

    @Test
    fun typingNewNameCanceled() = runBlocking {
        coEvery { actions.askForNewName(any(), any()) } returns null

        val result = changeSchemaNameUseCase(mockk(), careSchema)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(ChangeSchemaNameUseCase.Fail.NewNameNotTyped::class.java)
        }
        coVerify(exactly = 0) {
            careSchemaDao.update(*anyVararg())
        }
    }

    @Test
    fun successfullyChangedName() = runBlocking {
        coEvery { actions.askForNewName(any(), any()) } returns "Custom OMO"

        val result = changeSchemaNameUseCase(mockk(), careSchema)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careSchemaDao.update(careSchema.copy(name = "Custom OMO"))
        }
    }
}
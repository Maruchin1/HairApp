package com.example.edit_care_schema.use_case

import arrow.core.getOrHandle
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.ui.DialogService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ChangeSchemaNameUseCaseTest {
    private val dialogService: DialogService = mockk()
    private val careSchemaDao: CareSchemaDao = mockk()
    private val changeSchemaNameTitle = "Change schema name"
    private val careSchema = CareSchema(id = 1, name = "OMO")
    private val changeSchemaNameUseCase by lazy {
        ChangeSchemaNameUseCase(dialogService, careSchemaDao, changeSchemaNameTitle)
    }

    @Before
    fun before() {
        coJustRun { careSchemaDao.update(*anyVararg()) }
    }

    @Test
    fun whenTypingNewNameCanceled_ReturnNewNameNotTypedFail() = runBlocking {
        coEvery { dialogService.typeText(any(), any(), any()) } returns null

        val result = changeSchemaNameUseCase(mockk(), careSchema)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(ChangeSchemaNameUseCase.Fail.NewNameNotTyped::class.java)
        }
    }

    @Test
    fun whenTypingNewNameCanceled_DoNotUpdateSchemaInDb() = runBlocking {
        coEvery { dialogService.typeText(any(), any(), any()) } returns null

        changeSchemaNameUseCase(mockk(), careSchema)

        coVerify(exactly = 0) {
            careSchemaDao.update(*anyVararg())
        }
    }

    @Test
    fun whenNewNameTyped_ReturnRight() = runBlocking {
        coEvery { dialogService.typeText(any(), any(), any()) } returns "Custom OMO"

        val result = changeSchemaNameUseCase(mockk(), careSchema)

        assertThat(result.isRight()).isTrue()
    }

    @Test
    fun whenNewNameTyped_UpdateSchemaInDb() = runBlocking {
        coEvery { dialogService.typeText(any(), any(), any()) } returns "Custom OMO"

        changeSchemaNameUseCase(mockk(), careSchema)

        coVerify {
            careSchemaDao.update(careSchema.copy(name = "Custom OMO"))
        }
    }
}
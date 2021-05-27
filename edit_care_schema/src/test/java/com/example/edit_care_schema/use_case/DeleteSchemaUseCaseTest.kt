package com.example.edit_care_schema.use_case

import android.content.Context
import arrow.core.getOrHandle
import arrow.core.handleError
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.ui.DialogService
import com.example.edit_care_schema.R
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteSchemaUseCaseTest {
    private val context: Context = mockk()
    private val schema = CareSchema(id = 1, name = "OMO")
    private val dialogService: DialogService = mockk()
    private val careSchemaDao: CareSchemaDao = mockk()
    private val deleteSchemaUseCase by lazy {
        DeleteSchemaUseCase(dialogService, careSchemaDao)
    }

    @Before
    fun before() {
        every { context.getString(R.string.delete_care_schema) } returns "Usuń schemat pielęgnacji"
        coJustRun { careSchemaDao.delete(*anyVararg()) }
    }

    @Test
    fun whenSchemaIsNull_ReturnNoCareSchemaFail() = runBlocking {
        coEvery { dialogService.confirm(any(), any(), any()) } returns true

        val result = deleteSchemaUseCase(context, null)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(DeleteSchemaUseCase.Fail.NoCareSchema::class.java)
        }
    }

    @Test
    fun whenConfirmed_ReturnRight() = runBlocking {
        coEvery { dialogService.confirm(any(), any(), any()) } returns true

        val result = deleteSchemaUseCase(context, schema)

        assertThat(result.isRight()).isTrue()
    }

    @Test
    fun whenConfirmed_DeleteSchemaFormDb() = runBlocking {
        coEvery { dialogService.confirm(any(), any(), any()) } returns true

        deleteSchemaUseCase(context, schema)

        coVerify {
            careSchemaDao.delete(schema)
        }
    }

    @Test
    fun whenNotConfirmed_IsNotConfirmed() = runBlocking {
        coEvery { dialogService.confirm(any(), any(), any()) } returns false

        val result = deleteSchemaUseCase(context, schema)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(DeleteSchemaUseCase.Fail.NotConfirmed::class.java)
        }
    }

    @Test
    fun whenNotConfirmed_DoNotDeleteSchemaFromDb() = runBlocking {
        coEvery { dialogService.confirm(any(), any(), any()) } returns false

        deleteSchemaUseCase(context, schema)

        coVerify(exactly = 0) {
            careSchemaDao.delete(*anyVararg())
        }
    }
}
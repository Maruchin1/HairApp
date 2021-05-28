package com.example.edit_care_schema.use_case

import android.content.Context
import arrow.core.getOrHandle
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.example.edit_care_schema.R
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteSchemaUseCaseTest {
    private val context: Context = mockk()
    private val schema = CareSchema(id = 1, name = "OMO")
    private val actions: DeleteSchemaUseCase.Actions = mockk()
    private val careSchemaDao: CareSchemaDao = mockk()
    private val deleteSchemaUseCase by lazy {
        DeleteSchemaUseCase(actions, careSchemaDao)
    }

    @Before
    fun before() {
        every { context.getString(R.string.delete_care_schema) } returns "Usuń schemat pielęgnacji"
        coJustRun { careSchemaDao.delete(*anyVararg()) }
    }

    @Test
    fun schemaIsNull() = runBlocking {
        coEvery { actions.confirmDeletion(any()) } returns true

        val result = deleteSchemaUseCase(context, null)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(DeleteSchemaUseCase.Fail.NoCareSchema::class.java)
        }
        coVerify(exactly = 0) {
            careSchemaDao.delete(*anyVararg())
        }
    }

    @Test
    fun deletionNotConfirmed() = runBlocking {
        coEvery { actions.confirmDeletion(any()) } returns false

        val result = deleteSchemaUseCase(context, schema)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(DeleteSchemaUseCase.Fail.NotConfirmed::class.java)
        }
        coVerify(exactly = 0) {
            careSchemaDao.delete(*anyVararg())
        }
    }

    @Test
    fun successfullyDeleted() = runBlocking {
        coEvery { actions.confirmDeletion(any()) } returns true

        val result = deleteSchemaUseCase(context, schema)

        assertThat(result.isRight()).isTrue()
        coVerify {
            careSchemaDao.delete(schema)
        }
    }
}
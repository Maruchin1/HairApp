package com.example.cares_list.use_case

import android.app.Activity
import arrow.core.getOrHandle
import com.example.corev2.entities.CareSchema
import com.example.corev2.navigation.CareDetailsDestination
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class OpenAddNewCareUseCaseTest {
    private val actions: OpenAddNewCareUseCase.Actions = mockk()
    private val careDetailsDestination: CareDetailsDestination = mockk()
    private val activity: Activity = mockk()
    private val selectedCareSchema = CareSchema(id = 1, name = "OMO")
    private val openAddNewCareUseCase by lazy {
        OpenAddNewCareUseCase(actions, careDetailsDestination)
    }

    @Before
    fun before() {
        coEvery { actions.selectCareSchema(any()) } returns selectedCareSchema
        coJustRun { careDetailsDestination.navigate(any(), any()) }
    }

    @Test
    fun careSchemaNotSelected() = runBlocking {
        coEvery { actions.selectCareSchema(any()) } returns null

        val result = openAddNewCareUseCase(activity)

        assertThat(result.isLeft()).isTrue()
        result.getOrHandle {
            assertThat(it).isInstanceOf(OpenAddNewCareUseCase.Fail.CareSchemaNotSelected::class.java)
        }
    }

    @Test
    fun successfullyNavigated() = runBlocking {
        val result = openAddNewCareUseCase(activity)

        assertThat(result.isRight()).isTrue()
        verify {
            careDetailsDestination.navigate(
                activity,
                CareDetailsDestination.Params.AddNewCare(selectedCareSchema.id)
            )
        }
    }
}
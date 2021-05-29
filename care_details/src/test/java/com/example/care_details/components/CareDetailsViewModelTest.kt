package com.example.care_details.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.corev2.service.ClockService
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class CareDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val clockService: ClockService = mockk()
    private val viewModel by lazy {
        CareDetailsViewModel(clockService)
    }

    @Test
    fun careDate_IsCurrentDateByDefault() = runBlocking {
        val fakeNow = LocalDate.now()
        every { clockService.getNow() } returns fakeNow


        val result = viewModel.careDate.asFlow().firstOrNull()

        assertThat(result).isEqualTo(fakeNow)
    }
}
package com.example.cares_list.components

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care
import com.example.corev2.service.ClockService
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class CaresListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val careDao: CareDao = mockk()
    private val clockService: ClockService = mockk()
    private val viewModel by lazy {
        CaresListViewModel(careDao, clockService)
    }

    @Before
    fun before() {
        every { careDao.getAllCares() } returns flowOf(listOf())
        every { clockService.getNow() } returns LocalDate.now()
    }

    @Test
    fun emitToday() = runBlocking {
        val fakeNow = LocalDate.now()
        coEvery { clockService.getNow() } returns fakeNow

        val result = viewModel.today.asFlow().firstOrNull()

        assertThat(result).isEqualTo(fakeNow)
    }

    @Test
    fun emitDaysFromLastCare() = runBlocking {
        val caresFromDb = listOf(
            Care(
                id = 1,
                schemaName = "OMO",
                date = LocalDate.of(2021, 5, 11),
                notes = ""
            ),
            Care(
                id = 2,
                schemaName = "OMO",
                date = LocalDate.of(2021, 5, 20),
                notes = ""
            ),
            Care(
                id = 3,
                schemaName = "OMO",
                date = LocalDate.of(2021, 5, 16),
                notes = ""
            )
        )
        val fakeNow = LocalDate.of(2021, 5, 25)
        every { careDao.getAllCares() } returns flowOf(caresFromDb)
        every { clockService.getNow() } returns fakeNow

        val result = viewModel.daysFromLastCare.asFlow().firstOrNull()

        assertThat(result).isEqualTo(5)
    }

    @Test
    fun emitOrderedCares() = runBlocking {
        val caresFromDb = listOf(
            Care(
                id = 1,
                schemaName = "OMO",
                date = LocalDate.of(2021, 5, 11),
                notes = ""
            ),
            Care(
                id = 2,
                schemaName = "OMO",
                date = LocalDate.of(2021, 5, 20),
                notes = ""
            ),
            Care(
                id = 3,
                schemaName = "OMO",
                date = LocalDate.of(2021, 5, 16),
                notes = ""
            )
        )
        every { careDao.getAllCares() } returns flowOf(caresFromDb)

        val result = viewModel.orderedCares.asFlow().firstOrNull()

        assertThat(result).containsExactly(
            caresFromDb[1],
            caresFromDb[2],
            caresFromDb[0]
        ).inOrder()
    }

    @Test
    fun noCaresIsTrue_WhenNoCaresInDb() = runBlocking {
        val result = viewModel.noCares.asFlow().firstOrNull()

        assertThat(result).isTrue()
    }

    @Test
    fun noCaresIsFalse_WhenCaresAvailableInDb() = runBlocking {
        val caresFromDb = listOf(
            Care(
                id = 1,
                schemaName = "OMO",
                date = LocalDate.of(2021, 5, 11),
                notes = ""
            )
        )
        every { careDao.getAllCares() } returns flowOf(caresFromDb)

        val result = viewModel.noCares.asFlow().firstOrNull()

        assertThat(result).isFalse()
    }
}
package com.example.cares_list.components

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import arrow.core.Either
import com.example.cares_list.use_case.AddNewCareUseCase
import com.example.corev2.dao.CareDao
import com.example.corev2.entities.Care
import com.example.corev2.relations.CareWithStepsAndPhotos
import com.example.corev2.service.ClockService
import com.example.navigation.CareDetailsDestination
import com.example.navigation.CareDetailsParams
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class CaresListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val careDao: CareDao = mockk()
    private val clockService: ClockService = mockk()
    private val addNewCareUseCase: AddNewCareUseCase = mockk()
    private val careDetailsDestination = mockk<CareDetailsDestination>()
    private val viewModel by lazy {
        CaresListViewModel(careDao, clockService, addNewCareUseCase, careDetailsDestination)
    }

    @Before
    fun before() {
        every { careDao.getAllCares() } returns flowOf(listOf())
        every { clockService.getNow() } returns LocalDateTime.now()
    }

    @Test
    fun emitToday() = runBlocking {
        val fakeNow = LocalDateTime.now()
        coEvery { clockService.getNow() } returns fakeNow

        val result = viewModel.today.asFlow().firstOrNull()

        assertThat(result).isEqualTo(fakeNow)
    }

    @Test
    fun emitDaysFromLastCare() = runBlocking {
        val caresFromDb = listOf(
            CareWithStepsAndPhotos(
                care = Care(
                    id = 1,
                    schemaName = "OMO",
                    date = LocalDateTime.of(2021, 5, 11, 0, 0),
                    notes = ""
                ),
                steps = listOf(),
                photos = listOf()
            ),
            CareWithStepsAndPhotos(
                care = Care(
                    id = 2,
                    schemaName = "OMO",
                    date = LocalDateTime.of(2021, 5, 20, 0, 0),
                    notes = ""
                ),
                steps = listOf(),
                photos = listOf()
            ),
            CareWithStepsAndPhotos(
                care = Care(
                    id = 3,
                    schemaName = "OMO",
                    date = LocalDateTime.of(2021, 5, 16, 0, 0),
                    notes = ""
                ),
                steps = listOf(),
                photos = listOf()
            )
        )
        val fakeNow = LocalDateTime.of(2021, 5, 25, 0, 0)
        every { careDao.getAllCares() } returns flowOf(caresFromDb)
        every { clockService.getNow() } returns fakeNow

        val result = viewModel.daysFromLastCare.asFlow().firstOrNull()

        assertThat(result).isEqualTo(5)
    }

    @Test
    fun emitOrderedCares() = runBlocking {
        val caresFromDb = listOf(
            CareWithStepsAndPhotos(
                care = Care(
                    id = 1,
                    schemaName = "OMO",
                    date = LocalDateTime.of(2021, 5, 11, 0, 0),
                    notes = ""
                ),
                steps = listOf(),
                photos = listOf()
            ),
            CareWithStepsAndPhotos(
                care = Care(
                    id = 2,
                    schemaName = "OMO",
                    date = LocalDateTime.of(2021, 5, 20, 0, 0),
                    notes = ""
                ),
                steps = listOf(),
                photos = listOf()
            ),
            CareWithStepsAndPhotos(
                care = Care(
                    id = 3,
                    schemaName = "OMO",
                    date = LocalDateTime.of(2021, 5, 16, 0, 0),
                    notes = ""
                ),
                steps = listOf(),
                photos = listOf()
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
            CareWithStepsAndPhotos(
                care = Care(
                    id = 1,
                    schemaName = "OMO",
                    date = LocalDateTime.of(2021, 5, 11, 0, 0),
                    notes = ""
                ),
                steps = listOf(),
                photos = listOf()
            )
        )
        every { careDao.getAllCares() } returns flowOf(caresFromDb)

        val result = viewModel.noCares.asFlow().firstOrNull()

        assertThat(result).isFalse()
    }

    @Test
    fun onAddCareClick_InvokeOpenAddNewCareUseCare() = runBlocking {
        val activity: Activity = mockk()
        coEvery { addNewCareUseCase(any()) } returns Either.Right(1)
        coJustRun { careDetailsDestination.navigate(any(), any()) }

        viewModel.onAddCareClick(activity)

        coVerify {
            addNewCareUseCase(activity)
            careDetailsDestination.navigate(activity, CareDetailsParams(1))
        }
    }
}
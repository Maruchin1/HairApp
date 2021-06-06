package com.example.care_schemas_list

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.corev2.dao.CareSchemaDao
import com.example.corev2.entities.CareSchema
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.navigation.CareSchemaDetailsDestination
import com.example.navigation.CareSchemaDetailsParams
import com.example.testing.rules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CareSchemasListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val careSchemaDao: CareSchemaDao = mockk()
    private val editCareSchemaDestination = mockk<CareSchemaDetailsDestination>()
    private val viewModel by lazy {
        CareSchemasListViewModel(careSchemaDao, editCareSchemaDestination)
    }

    @Before
    fun before() {
        every { careSchemaDao.getAll() } returns flowOf(listOf())
    }

    @Test
    fun careSchemas_EmitAllSchemasFromDb_SortedAlphabetically() = runBlocking {
        val schemasFromDb = listOf(
            CareSchemaWithSteps(
                careSchema = CareSchema(
                    id = 3,
                    name = "Własny"
                ),
                steps = listOf()
            ),
            CareSchemaWithSteps(
                careSchema = CareSchema(
                    id = 1,
                    name = "OMO"
                ),
                steps = listOf()
            ),
            CareSchemaWithSteps(
                careSchema = CareSchema(
                    id = 2,
                    name = "CG"
                ),
                steps = listOf()
            ),
        )
        every { careSchemaDao.getAll() } returns flowOf(schemasFromDb)

        val result = viewModel.careSchemas.asFlow().firstOrNull()

        assertThat(result).isEqualTo(
            listOf(
                schemasFromDb[2],
                schemasFromDb[1],
                schemasFromDb[0]
            )
        )
    }

    @Test
    fun addNewSchema_InsertNewSchemaToDb() = runBlocking {
        coJustRun { careSchemaDao.insert(any()) }

        viewModel.addNewSchema("Własny")

        coVerify {
            careSchemaDao.insert(CareSchema(id = 0, name = "Własny"))
        }
    }

    @Test
    fun openSchema_NavigateToEditCareSchemaDestination() {
        val activity: Activity = mockk()
        coJustRun { editCareSchemaDestination.navigate(any(), any()) }

        viewModel.openSchema(activity, 1)

        coVerify {
            editCareSchemaDestination.navigate(
                activity,
                CareSchemaDetailsParams(1)
            )
        }
    }
}
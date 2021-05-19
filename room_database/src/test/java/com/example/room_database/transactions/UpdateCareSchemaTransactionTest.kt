package com.example.room_database.transactions

import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import com.example.room_database.dao.CareSchemaDao
import com.example.room_database.dao.CareSchemaStepDao
import com.example.room_database.entities.CareSchemaEntity
import com.example.room_database.entities.CareSchemaStepEntity
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class UpdateCareSchemaTransactionTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module {
            single { mockk<CareSchemaDao>() }
            single { mockk<CareSchemaStepDao>() }
            factory {
                UpdateCareSchemaTransaction(
                    careSchemaDao = get(),
                    careSchemaStepDao = get()
                )
            }
        })
    }

    private val careSchemaDao: CareSchemaDao by inject()
    private val careSchemaStepDao: CareSchemaStepDao by inject()
    private val transaction: UpdateCareSchemaTransaction by inject()

    private val existingSteps = listOf(
        CareSchemaStepEntity(
            id = 1,
            type = CareStep.Type.CONDITIONER,
            order = 0,
            careSchemaId = 1
        ),
        CareSchemaStepEntity(
            id = 2,
            type = CareStep.Type.SHAMPOO,
            order = 1,
            careSchemaId = 1
        ),
        CareSchemaStepEntity(
            id = 3,
            type = CareStep.Type.CONDITIONER,
            order = 2,
            careSchemaId = 1
        )
    )
    private val update = CareSchema(
        id = 1,
        name = "Własne",
        steps = listOf(
            CareSchemaStep(
                id = 2,
                type = CareStep.Type.SHAMPOO,
                order = 0,
            ),
            CareSchemaStep(
                id = 3,
                type = CareStep.Type.STYLIZER,
                order = 1,
            ),
            CareSchemaStep(
                id = 4,
                type = CareStep.Type.OIL,
                order = 2,
            )
        )
    )

    @Before
    fun before() {
        every { careSchemaStepDao.findByCareSchema(any()) } returns flowOf(existingSteps)
        coJustRun { careSchemaDao.update(any()) }
        coJustRun { careSchemaStepDao.delete(*anyVararg()) }
        coJustRun { careSchemaStepDao.insert(*anyVararg()) }
        coJustRun { careSchemaStepDao.update(*anyVararg()) }
    }

    @Test
    fun updateSchemaInRepo() = runBlocking {
        transaction(update)

        coVerify {
            careSchemaDao.update(
                CareSchemaEntity(
                    id = update.id,
                    name = update.name
                )
            )
        }
    }

    @Test
    fun deleteExistingSteps_WhichAreNotInUpdate() = runBlocking {
        transaction(update)

        coVerify {
            careSchemaStepDao.delete(
                CareSchemaStepEntity(
                    id = 1,
                    type = CareStep.Type.CONDITIONER,
                    order = 0,
                    careSchemaId = 1
                )
            )
        }
    }

    @Test
    fun insertStepsFromUpdate_WhichNotExist() = runBlocking {
        transaction(update)

        coVerify {
            careSchemaStepDao.insert(
                CareSchemaStepEntity(
                    id = 4,
                    type = CareStep.Type.OIL,
                    order = 2,
                    careSchemaId = 1
                )
            )
        }
    }

    @Test
    fun updateExistingSteps_WhichWereChanged() = runBlocking {
        transaction(update)

        coVerify {
            careSchemaStepDao.update(
                CareSchemaStepEntity(
                    id = 2,
                    type = CareStep.Type.SHAMPOO,
                    order = 0,
                    careSchemaId = 1
                ),
                CareSchemaStepEntity(
                    id = 3,
                    type = CareStep.Type.STYLIZER,
                    order = 1,
                    careSchemaId = 1
                )
            )
        }
    }
}
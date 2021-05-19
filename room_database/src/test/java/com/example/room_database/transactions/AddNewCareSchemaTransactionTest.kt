package com.example.room_database.transactions

import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep
import com.example.room_database.dao.CareSchemaDao
import com.example.room_database.dao.CareSchemaStepDao
import com.example.room_database.entities.CareSchemaEntity
import com.example.room_database.entities.CareSchemaStepEntity
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class AddNewCareSchemaTransactionTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module {
            single { mockk<CareSchemaDao>() }
            single { mockk<CareSchemaStepDao>() }
            factory {
                AddNewCareSchemaTransaction(
                    careSchemaDao = get(),
                    careSchemaStepDao = get()
                )
            }
        })
    }

    private val careSchemaDao: CareSchemaDao by inject()
    private val careSchemaStepDao: CareSchemaStepDao by inject()
    private val transaction: AddNewCareSchemaTransaction by inject()

    private val addedSchemaId = 1L

    @Before
    fun before() {
        coEvery { careSchemaDao.insert(any()) } returns arrayOf(addedSchemaId)
        coJustRun { careSchemaStepDao.insert(*anyVararg()) }
    }

    private fun createNewSchema() = CareSchema(
        id = -1,
        name = "Własne",
        steps = listOf(
            CareSchemaStep(
                id = -1,
                type = CareStep.Type.CONDITIONER,
                order = 0,
            ),
            CareSchemaStep(
                id = -1,
                type = CareStep.Type.SHAMPOO,
                order = 1,
            )
        )
    )

    @Test
    fun insertNewSchema() = runBlocking {
        val careSchema = createNewSchema()

        transaction(careSchema)

        coVerify {
            careSchemaDao.insert(
                CareSchemaEntity(
                    id = -1,
                    name = careSchema.name,
                )
            )
        }
    }

    @Test
    fun insertSchemaSteps() = runBlocking {
        val careSchema = createNewSchema()

        transaction(careSchema)

        coVerify {
            careSchemaStepDao.insert(
                CareSchemaStepEntity(
                    id = -1,
                    type = CareStep.Type.CONDITIONER,
                    order = 0,
                    careSchemaId = addedSchemaId.toInt()
                ),
                CareSchemaStepEntity(
                    id = -1,
                    type = CareStep.Type.SHAMPOO,
                    order = 1,
                    careSchemaId = addedSchemaId.toInt()
                )
            )
        }
    }
}
package com.example.corev2.dao

import com.example.corev2.entities.CareSchema
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.ProductType
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.testing.rules.DatabaseTestRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CareSchemaDaoTest {

    @get:Rule
    val hairAppDatabaseTestRule = DatabaseTestRule()

    private val careSchemaDao by lazy { hairAppDatabaseTestRule.db.careSchemaDao() }
    private val careSchemaStepDao by lazy { hairAppDatabaseTestRule.db.careSchemaStepDao() }

    private val omoSchema = CareSchema(
        id = 0,
        name = "OMO"
    )
    private val cgSchema = CareSchema(
        id = 0,
        name = "CG"
    )
    private val omoSteps = arrayOf(
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.CONDITIONER,
            order = 0,
            careSchemaId = 1
        ),
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.SHAMPOO,
            order = 1,
            careSchemaId = 1
        ),
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.CONDITIONER,
            order = 2,
            careSchemaId = 1
        ),
    )
    private val cgSteps = arrayOf(
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.CONDITIONER,
            order = 0,
            careSchemaId = 2
        ),
        CareSchemaStep(
            id = 0,
            prouctType = ProductType.CONDITIONER,
            order = 1,
            careSchemaId = 2
        )
    )

    @Before
    fun before() {
        runBlocking {
            careSchemaDao.insert(omoSchema, cgSchema)
            careSchemaStepDao.insert(*omoSteps)
            careSchemaStepDao.insert(*cgSteps)
        }
    }

    @Test
    fun getAllSchemasWitSteps() = runBlocking {
        val result = careSchemaDao.getAll().firstOrNull()

        assertThat(result).containsExactly(
            CareSchemaWithSteps(
                careSchema = omoSchema.copy(id = 1),
                steps = listOf(
                    omoSteps[0].copy(id = 1),
                    omoSteps[1].copy(id = 2),
                    omoSteps[2].copy(id = 3)
                )
            ),
            CareSchemaWithSteps(
                careSchema = cgSchema.copy(id = 2),
                steps = listOf(
                    cgSteps[0].copy(id = 4),
                    cgSteps[1].copy(id = 5),
                )
            )
        )
        Unit
    }

    @Test
    fun getSchemaWithStepsById() = runBlocking {
        val result = careSchemaDao.getById(1).firstOrNull()

        assertThat(result).isEqualTo(
            CareSchemaWithSteps(
                careSchema = omoSchema.copy(id = 1),
                steps = listOf(
                    omoSteps[0].copy(id = 1),
                    omoSteps[1].copy(id = 2),
                    omoSteps[2].copy(id = 3)
                )
            )
        )
    }
}
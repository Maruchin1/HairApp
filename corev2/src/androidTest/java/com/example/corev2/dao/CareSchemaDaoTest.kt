package com.example.corev2.dao

import com.example.corev2.HairAppDatabaseTestRule
import com.example.corev2.entities.CareSchema
import com.example.corev2.relations.CareSchemaWithSteps
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CareSchemaDaoTest {

    @get:Rule
    val hairAppDatabaseTestRule = HairAppDatabaseTestRule()

    private val careSchemaDao by lazy { hairAppDatabaseTestRule.db.careSchemaDao() }

    @Before
    fun before() {
        val firstSchema = CareSchema(
            id = 0,
            name = "Schema 1"
        )
        val secondSchema = CareSchema(
            id = 0,
            name = "Schema 2"
        )
        runBlocking {
            careSchemaDao.insert(firstSchema, secondSchema)
        }
    }

    @Test
    fun getAllSchemasWitSteps() = runBlocking {
        val result = careSchemaDao.getAll().first()

        assertThat(result).containsExactly(
            CareSchemaWithSteps(
                careSchema = CareSchema(
                    id = 1,
                    name = "Schema 1"
                ),
                steps = listOf()
            ),
            CareSchemaWithSteps(
                careSchema = CareSchema(
                    id = 2,
                    name = "Schema 2"
                ),
                steps = listOf()
            )
        )
        Unit
    }

    @Test
    fun getSchemaWithStepsById() = runBlocking {
        val result = careSchemaDao.getById(1).first()

        assertThat(result).isEqualTo(
            CareSchemaWithSteps(
                careSchema = CareSchema(
                    id = 1,
                    name = "Schema 1"
                ),
                steps = listOf()
            )
        )
    }
}
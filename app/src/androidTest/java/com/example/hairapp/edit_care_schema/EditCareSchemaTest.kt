package com.example.hairapp.edit_care_schema

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.corev2.room_database.DatabaseInitializer
import com.example.corev2.room_database.HairAppDatabase
import com.example.hairapp.MainActivity
import com.example.hairapp.screen.CareSchemasListScreen
import com.example.hairapp.screen.HomeScreen
import com.example.hairapp.screen.KCareSchemaItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.inject

abstract class EditCareSchemaTest : KoinTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private val database: HairAppDatabase by inject()
    private val databaseInitializer: DatabaseInitializer by inject()

    @Before
    fun before() {
        HomeScreen {
            careSchemasListButton.click()
        }
        runBlocking {
            databaseInitializer.run {
                reset().join()
                checkIfInitialized().join()
            }
            delay(1_000)
        }
        CareSchemasListScreen {
            schemasRecycler {
                childAt<KCareSchemaItem>(1) {
                    click()
                }
            }
        }
    }

    @After
    fun after() {
        database.clearAllTables()
    }
}
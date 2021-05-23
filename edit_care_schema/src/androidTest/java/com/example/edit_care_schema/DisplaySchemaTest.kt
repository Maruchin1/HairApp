package com.example.edit_care_schema

import com.example.corev2.room_database.HairAppDatabase
import com.example.edit_care_schema.framework.EditCareSchemaScreen
import com.example.edit_care_schema.framework.KCareSchemaStepItem
import com.example.edit_care_schema.framework.testFlow
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DisplaySchemaTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    val screen = EditCareSchemaScreen()

    @Inject
    lateinit var hairAppDatabase: HairAppDatabase

    @Before
    fun before() {
        hiltRule.inject()
        hairAppDatabase.clearAllTables()
    }

    @Test
    fun displaySchemaName() = testFlow(hairAppDatabase) { _, _ ->
        screen {
            toolbar.hasTitle("OMO")
        }
    }

    @Test
    fun displaySchemaSteps() = testFlow(hairAppDatabase) { _, _ ->
        screen {
            stepsRecycler.hasSize(3)

            stepsRecycler.childAt<KCareSchemaStepItem>(0) {
                stepNumber.hasText("1")
                stepName.hasText(R.string.conditioner)
            }

            stepsRecycler.childAt<KCareSchemaStepItem>(1) {
                stepNumber.hasText("2")
                stepName.hasText(R.string.shampoo)
            }

            stepsRecycler.childAt<KCareSchemaStepItem>(2) {
                stepNumber.hasText("3")
                stepName.hasText(R.string.conditioner)
            }
        }
    }
}
package com.example.edit_care_schema

import com.agoda.kakao.dialog.KAlertDialog
import com.example.corev2.room_database.HairAppDatabase
import com.example.edit_care_schema.framework.EditCareSchemaScreen
import com.example.edit_care_schema.framework.KCareSchemaStepItem
import com.example.edit_care_schema.framework.KSelectProductTypeDialog
import com.example.edit_care_schema.framework.testFlow
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AddStepTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    val screen = EditCareSchemaScreen()
    val alertDialog = KAlertDialog()
    val selectProductTypeDialog = KSelectProductTypeDialog()

    @Inject
    lateinit var database: HairAppDatabase

    @Before
    fun before() {
        hiltRule.inject()
        database.clearAllTables()
    }

    @Test
    fun displaySelectProductTypeDialog() = testFlow(database) { _, _ ->
        screen {
            addStepButton.click()
        }
        alertDialog {
            title.hasText(R.string.product_type)
        }
        selectProductTypeDialog {
            conditioner.isVisible()
            shampoo.isVisible()
            oil.isVisible()
            emulsifier.isVisible()
            stylizer.isVisible()
            other.isVisible()
        }
    }

    @Test
    fun addOilAsLastItem() = testFlow(database) { _, _ ->
        screen {
            addStepButton.click()
        }
        selectProductTypeDialog {
            oil.click()
        }
        screen {
            stepsRecycler {
                hasSize(4)

                childAt<KCareSchemaStepItem>(3) {
                    stepNumber.hasText("4")
                    stepName.hasText(R.string.oil)
                }
            }
        }
    }
}
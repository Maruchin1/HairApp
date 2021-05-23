package com.example.edit_care_schema

import com.agoda.kakao.dialog.KAlertDialog
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
class DeleteStepTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    val screen = EditCareSchemaScreen()
    val alertDialog = KAlertDialog()

    @Inject
    lateinit var database: HairAppDatabase

    @Before
    fun before() {
        hiltRule.inject()
        database.clearAllTables()
    }

    @Test
    fun displayConfirmDialog() = testFlow(database) { _, _ ->
        screen {
            stepsRecycler {
                childAt<KCareSchemaStepItem>(1) {
                    longClick()
                }
            }
        }
        alertDialog {
            isDisplayed()
            title.hasText("Usunąć krok 2 Szampon?")
            positiveButton.hasText(R.string.confirm)
            negativeButton.hasText(R.string.cancel)
        }
    }

    @Test
    fun whenCancel_StillDisplayStep() = testFlow(database) { _, _ ->
        screen {
            stepsRecycler {
                childAt<KCareSchemaStepItem>(1) {
                    longClick()
                }
            }
        }
        alertDialog {
            negativeButton.click()
        }
        screen {
            stepsRecycler {
                hasSize(3)

                childAt<KCareSchemaStepItem>(1) {
                    isDisplayed()
                    stepNumber.hasText("2")
                    stepName.hasText(R.string.shampoo)
                }
            }
        }
    }

    @Test
    fun whenConfirm_RemoveStepFromList() = testFlow(database) { _, _ ->
        screen {
            stepsRecycler {
                childAt<KCareSchemaStepItem>(1) {
                    longClick()
                }
            }
        }
        alertDialog {
            positiveButton.click()
        }
        screen {
            stepsRecycler {
                hasSize(2)

                childAt<KCareSchemaStepItem>(1) {
                    isDisplayed()
                    stepNumber.hasText("2")
                    stepName.hasText(R.string.conditioner)
                }
            }
        }
    }
}
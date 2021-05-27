package com.example.hairapp.edit_care_schema

import com.agoda.kakao.dialog.KAlertDialog
import com.example.hairapp.R
import com.example.hairapp.screen.EditCareSchemaScreen
import com.example.hairapp.screen.KCareSchemaStepItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteStepTest : EditCareSchemaTest() {

    private val alertDialog = KAlertDialog()

    @Test
    fun displayConfirmDialog() {
        EditCareSchemaScreen {
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
    fun whenCancel_StillDisplayStep() {
        EditCareSchemaScreen {
            stepsRecycler {
                childAt<KCareSchemaStepItem>(1) {
                    longClick()
                }
            }
        }
        alertDialog {
            negativeButton.click()
        }
        EditCareSchemaScreen {
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
    fun whenConfirm_RemoveStepFromList() = runBlocking {
        EditCareSchemaScreen {
            stepsRecycler {
                childAt<KCareSchemaStepItem>(1) {
                    longClick()
                }
            }
        }
        alertDialog {
            positiveButton.click()
        }
        delay(1_000)
        EditCareSchemaScreen {
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
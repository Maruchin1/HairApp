package com.example.hairapp.edit_care_schema

import com.agoda.kakao.dialog.KAlertDialog
import com.example.edit_care_schema.R
import com.example.hairapp.screen.EditCareSchemaScreen
import com.example.hairapp.screen.KCareSchemaStepItem
import com.example.hairapp.screen.SelectProductTypeDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AddStepTest : EditCareSchemaTest() {

    val dialog = KAlertDialog()

    @Test
    fun displaySelectProductTypeDialog() {
        EditCareSchemaScreen {
            addStepButton.click()
        }
        dialog {
            title.hasText(R.string.product_type)
        }
        SelectProductTypeDialog {
            conditioner.isVisible()
            shampoo.isVisible()
            oil.isVisible()
            emulsifier.isVisible()
            stylizer.isVisible()
            other.isVisible()
        }
    }

    @Test
    fun addOilAsLastItem() = runBlocking {
        EditCareSchemaScreen {
            addStepButton.click()
        }
        SelectProductTypeDialog {
            oil.click()
        }
        delay(1_000)
        EditCareSchemaScreen {
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
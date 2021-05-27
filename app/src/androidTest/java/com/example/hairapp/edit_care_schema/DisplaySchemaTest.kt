package com.example.hairapp.edit_care_schema

import com.example.hairapp.R
import com.example.hairapp.screen.EditCareSchemaScreen
import com.example.hairapp.screen.KCareSchemaStepItem
import org.junit.Test

class DisplaySchemaTest : EditCareSchemaTest() {

    @Test
    fun displaySchemaName() {
        EditCareSchemaScreen {
            toolbar.hasTitle("OMO")
        }
    }

    @Test
    fun displaySchemaSteps() {
        EditCareSchemaScreen {
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
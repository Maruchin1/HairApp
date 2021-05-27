package com.example.hairapp.edit_care_schema

import com.agoda.kakao.dialog.KAlertDialog
import com.example.hairapp.R
import com.example.hairapp.screen.CareSchemasListScreen
import com.example.hairapp.screen.EditCareSchemaScreen
import com.example.hairapp.screen.KCareSchemaItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteSchemaTest : EditCareSchemaTest() {

    private val alertDialog = KAlertDialog()

    @Test
    fun openConfirmDialog() {
        EditCareSchemaScreen {
            deleteSchemaButton.click()
        }
        alertDialog {
            title.hasText(R.string.delete_care_schema)
            positiveButton.hasText(R.string.confirm)
            negativeButton.hasText(R.string.cancel)
        }
    }

    @Test
    fun whenCancel_StayStillDisplay() {
        EditCareSchemaScreen {
            deleteSchemaButton.click()
        }
        alertDialog {
            negativeButton.click()
        }
        EditCareSchemaScreen {
            toolbar.hasTitle(R.string.omo)
        }
    }

    @Test
    fun whenConfirm_RemoveSchema() = runBlocking {
        EditCareSchemaScreen {
            deleteSchemaButton.click()
        }
        alertDialog {
            positiveButton.click()
        }
        delay(1_000)
        CareSchemasListScreen {
            schemasRecycler {
                hasSize(1)
                childAt<KCareSchemaItem>(0) {
                    schemaName.hasText(R.string.cg)
                }
            }
        }
    }
}
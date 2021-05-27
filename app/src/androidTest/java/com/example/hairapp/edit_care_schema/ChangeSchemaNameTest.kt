package com.example.hairapp.edit_care_schema

import com.agoda.kakao.dialog.KAlertDialog
import com.example.hairapp.R
import com.example.hairapp.screen.EditCareSchemaScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ChangeSchemaNameTest : EditCareSchemaTest() {

    private val alertDialog = KAlertDialog()

    @Test
    fun openTypeNewNameDialog() {
        EditCareSchemaScreen {
            changeNameButton.click()
        }
        alertDialog {
            title.hasText(R.string.change_care_schema_name)
            positiveButton.hasText(R.string.save)
            neutralButton.hasText(R.string.close)
        }
    }

    @Test
    fun displayCurrentSchemaNameInInput() {
        EditCareSchemaScreen {
            changeNameButton.click()

            dialogInput.hasText("OMO")
        }
    }

    @Test
    fun saveNewName_AndDisplayItInToolbar() = runBlocking {
        EditCareSchemaScreen {
            changeNameButton.click()

            dialogInput {
                clearText()
                typeText("Custom")
            }
        }
        alertDialog {
            positiveButton.click()
        }
        delay(1_000)
        EditCareSchemaScreen {
            toolbar.hasTitle("Custom")
        }
    }
}
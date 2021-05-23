package com.example.edit_care_schema

import com.agoda.kakao.dialog.KAlertDialog
import com.example.corev2.room_database.HairAppDatabase
import com.example.edit_care_schema.framework.EditCareSchemaScreen
import com.example.edit_care_schema.framework.testFlow
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ChangeSchemaNameTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    val screen = EditCareSchemaScreen()
    val alertDialog = KAlertDialog()

    @Inject
    lateinit var hairAppDatabase: HairAppDatabase

    @Before
    fun before() {
        hiltRule.inject()
        hairAppDatabase.clearAllTables()
    }

    @Test
    fun openTypeNewNameDialog() = testFlow(hairAppDatabase) { _, _ ->
        screen {
            changeNameButton.click()
        }
        alertDialog {
            title.hasText(R.string.change_care_schema_name)
            positiveButton.hasText(R.string.save)
            neutralButton.hasText(R.string.close)
        }
    }

    @Test
    fun displayCurrentSchemaNameInInput() = testFlow(hairAppDatabase) { _, _ ->
        screen {
            changeNameButton.click()

            dialogInput.hasText("OMO")
        }
    }

    @Test
    fun saveNewName_AndDisplayItInToolbar() = testFlow(hairAppDatabase) { _, _ ->
        screen {
            changeNameButton.click()

            dialogInput {
                clearText()
                typeText("Custom")
            }
        }
        alertDialog {
            positiveButton.click()
        }
        screen {
            toolbar.hasTitle("Custom")
        }
    }
}
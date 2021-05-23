package com.example.edit_care_schema

import androidx.lifecycle.Lifecycle
import com.agoda.kakao.dialog.KAlertDialog
import com.example.corev2.room_database.HairAppDatabase
import com.example.edit_care_schema.framework.EditCareSchemaScreen
import com.example.edit_care_schema.framework.testFlow
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DeleteSchemaTest {

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
    fun openConfirmDialog() = testFlow(database) { _, _ ->
        screen {
            deleteSchemaButton.click()
        }
        alertDialog {
            title.hasText(R.string.delete_care_schema)
            positiveButton.hasText(R.string.confirm)
            negativeButton.hasText(R.string.cancel)
        }
    }

    @Test
    fun whenCancel_StayStillDisplay() = testFlow(database) { scenario, _ ->
        screen {
            deleteSchemaButton.click()
        }
        alertDialog {
            negativeButton.click()
        }
        assertThat(scenario.state).isEqualTo(Lifecycle.State.RESUMED)
        screen {
            toolbar.hasTitle(R.string.omo)
        }
    }

    @Test
    fun whenConfirm_FinishActivity() = testFlow(database) { scenario, _ ->
        screen {
            deleteSchemaButton.click()
        }
        alertDialog {
            positiveButton.click()
        }
        delay(500)
        assertThat(scenario.state).isEqualTo(Lifecycle.State.DESTROYED)
    }

    @Test
    fun whenConfirm_RemoveSchemaFromDatabase() = testFlow(database) { _, schemaId ->
        screen {
            deleteSchemaButton.click()
        }
        alertDialog {
            positiveButton.click()
        }
        delay(500)
        val careSchema = database.careSchemaDao().getById(schemaId).firstOrNull()
        assertThat(careSchema).isNull()
    }
}
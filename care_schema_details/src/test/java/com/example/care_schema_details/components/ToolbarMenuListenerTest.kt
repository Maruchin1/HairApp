package com.example.care_schema_details.components

import android.content.Context
import android.view.MenuItem
import com.example.care_schema_details.R
import com.example.common.modals.AppDialog
import com.example.testing.CoroutinesTestRule
import io.mockk.*
import org.junit.Rule
import org.junit.Test

class ToolbarMenuListenerTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val context: Context = mockk()
    private val viewModel: CareSchemaDetailsViewModel = mockk()
    private val appDialog: AppDialog = mockk()

    private val toolbarMenuListener by lazy {
        ToolbarMenuListener(context, coroutinesTestRule.testScope, viewModel, appDialog)
    }

    @Test
    fun optionChangeSchemaName_AskForNewName_AndChangeInViewModel() {
        val menuItem = mockOptionChangeSchemaNameItem()
        val changeSchemaNameText = mockChangeSchemaNameResource()
        val newSchemaNameText = mockTypeNewSchemaNameInput()
        coJustRun { viewModel.changeSchemaName(any()) }

        toolbarMenuListener.onMenuItemClick(menuItem)

        coVerify {
            appDialog.typeText(context, changeSchemaNameText)
            viewModel.changeSchemaName(newSchemaNameText)
        }
    }

    private fun mockOptionChangeSchemaNameItem(): MenuItem = mockk {
        every { itemId } returns R.id.option_change_schema_name
    }

    private fun mockChangeSchemaNameResource(): String {
        return "Zmień nazwę schematu".also {
            every { context.getString(R.string.change_care_schema_name)} returns it
        }
    }

    private fun mockTypeNewSchemaNameInput(): String {
        return "CG".also {
            coEvery { appDialog.typeText(any(), any()) } returns it
        }
    }

    @Test
    fun optionDeleteSchema_DeleteSchemaInViewModel_WhenConfirmed() {
        val menuItem = mockOptionDeleteSchemaItem()
        val deleteCareSchemaText = mockDeleteCareSchemaText()
        coEvery { appDialog.confirm(any(), any()) } returns true
        coJustRun { viewModel.deleteSchema() }

        toolbarMenuListener.onMenuItemClick(menuItem)

        coVerify {
            appDialog.confirm(context, deleteCareSchemaText)
            viewModel.deleteSchema()
        }
    }

    private fun mockOptionDeleteSchemaItem(): MenuItem = mockk {
        every { itemId } returns R.id.option_delete_schema
    }

    private fun mockDeleteCareSchemaText(): String {
        return "Usuń schemat pielęgnacji".also {
            every { context.getString(R.string.delete_care_schema) } returns it
        }
    }
}
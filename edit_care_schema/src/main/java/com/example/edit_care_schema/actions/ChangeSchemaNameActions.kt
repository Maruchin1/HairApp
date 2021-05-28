package com.example.edit_care_schema.actions

import android.content.Context
import com.example.corev2.ui.DialogService
import com.example.edit_care_schema.R
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase

internal class ChangeSchemaNameActions(
    private val dialogService: DialogService
) : ChangeSchemaNameUseCase.Actions {

    override suspend fun askForNewName(context: Context, currentName: String): String? {
        return dialogService.typeText(
            context = context,
            title = context.getString(R.string.change_care_schema_name),
            currentValue = currentName
        )
    }
}
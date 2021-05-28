package com.example.edit_care_schema.actions

import android.content.Context
import com.example.corev2.ui.DialogService
import com.example.edit_care_schema.R
import com.example.edit_care_schema.use_case.DeleteSchemaUseCase

internal class DeleteSchemaActions(
    private val dialogService: DialogService
) : DeleteSchemaUseCase.Actions {

    override suspend fun confirmDeletion(context: Context): Boolean {
        return dialogService.confirm(
            context = context,
            title = context.getString(R.string.delete_care_schema)
        )
    }
}
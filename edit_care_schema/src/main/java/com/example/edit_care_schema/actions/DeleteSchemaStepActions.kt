package com.example.edit_care_schema.actions

import android.content.Context
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.ui.DialogService
import com.example.edit_care_schema.R
import com.example.edit_care_schema.use_case.DeleteSchemaStepUseCase

internal class DeleteSchemaStepActions(
    private val dialogService: DialogService
) : DeleteSchemaStepUseCase.Actions {

    override suspend fun askForConfirmation(
        context: Context,
        stepToDelete: CareSchemaStep
    ): Boolean {
        return dialogService.confirm(
            context = context,
            title = buildConfirmationTitle(context, stepToDelete)
        )
    }

    private fun buildConfirmationTitle(context: Context, stepToDelete: CareSchemaStep): String {
        val deleteStep = context.getString(R.string.delete_step)
        val order = stepToDelete.order + 1
        val type = context.getString(stepToDelete.productType.resId)
        return "$deleteStep $order $type?"
    }
}
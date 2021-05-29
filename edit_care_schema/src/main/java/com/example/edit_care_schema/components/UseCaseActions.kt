package com.example.edit_care_schema.components

import android.content.Context
import com.example.corev2.entities.CareSchemaStep
import com.example.corev2.entities.Product
import com.example.corev2.ui.DialogService
import com.example.edit_care_schema.R
import com.example.edit_care_schema.use_case.AddSchemaStepUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaStepUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaUseCase

internal class UseCaseActions(
    private val dialogService: DialogService
) : AddSchemaStepUseCase.Actions,
    ChangeSchemaNameUseCase.Actions,
    DeleteSchemaUseCase.Actions,
    DeleteSchemaStepUseCase.Actions {

    override suspend fun askForProductType(context: Context): Product.Type? {
        return dialogService.selectProductType(context)
    }

    override suspend fun askForNewName(context: Context, currentName: String): String? {
        return dialogService.typeText(
            context = context,
            title = context.getString(R.string.change_care_schema_name),
            currentValue = currentName
        )
    }

    override suspend fun confirmSchemaDeletion(context: Context): Boolean {
        return dialogService.confirm(
            context = context,
            title = context.getString(R.string.delete_care_schema)
        )
    }

    override suspend fun confirmStepDeletion(
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
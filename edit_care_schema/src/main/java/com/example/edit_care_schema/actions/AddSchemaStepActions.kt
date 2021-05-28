package com.example.edit_care_schema.actions

import android.content.Context
import com.example.corev2.entities.Product
import com.example.corev2.ui.DialogService
import com.example.edit_care_schema.use_case.AddSchemaStepUseCase

internal class AddSchemaStepActions(
    private val dialogService: DialogService
) : AddSchemaStepUseCase.Actions {

    override suspend fun askForProductType(context: Context): Product.Type? {
        return dialogService.selectProductType(context)
    }
}
package com.example.cares_list.components

import android.content.Context
import com.example.cares_list.use_case.AddNewCareUseCase
import com.example.corev2.entities.CareSchema
import com.example.corev2.relations.CareSchemaWithSteps
import com.example.corev2.ui.DialogService

internal class UseCaseActions(
    private val dialogService: DialogService
) : AddNewCareUseCase.Actions {

    override suspend fun selectCareSchema(context: Context): CareSchemaWithSteps? {
        return dialogService.selectCareSchema(context)
    }
}
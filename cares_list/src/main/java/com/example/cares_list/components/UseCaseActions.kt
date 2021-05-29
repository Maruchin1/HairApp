package com.example.cares_list.components

import android.content.Context
import com.example.cares_list.use_case.OpenAddNewCareUseCase
import com.example.corev2.entities.CareSchema
import com.example.corev2.ui.DialogService

internal class UseCaseActions(
    private val dialogService: DialogService
) : OpenAddNewCareUseCase.Actions {

    override suspend fun selectCareSchema(context: Context): CareSchema? {
        return dialogService.selectCareSchema(context)
    }
}
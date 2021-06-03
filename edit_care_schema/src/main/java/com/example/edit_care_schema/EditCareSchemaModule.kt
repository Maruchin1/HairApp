package com.example.edit_care_schema

import com.example.edit_care_schema.components.CareSchemaStepsAdapter
import com.example.edit_care_schema.components.CareSchemaStepsTouchHelperCallback
import com.example.edit_care_schema.components.EditCareSchemaActivity
import com.example.edit_care_schema.components.EditCareSchemaViewModel
import com.example.edit_care_schema.components.UseCaseActions
import com.example.edit_care_schema.use_case.AddSchemaStepUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaStepUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaUseCase
import com.example.edit_care_schema.use_case.UpdateStepsOrderUseCase
import com.example.navigation.DestinationType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val editCareSchemaModule = module {
    viewModel {
        EditCareSchemaViewModel(
            careSchemaDao = get(),
            careSchemaStepDao = get(),
            changeSchemaNameUseCase = get(),
            deleteSchemaUseCase = get(),
            addSchemaStepUseCase = get(),
            deleteSchemaStepUseCase = get()
        )
    }
    factory(named(DestinationType.CARE_SCHEMA_DETAILS)) {
        EditCareSchemaActivity::class.java
    }
    factory {
        CareSchemaStepsAdapter(
            context = androidContext(),
            careSchemaStepsTouchHelperCallback = get()
        )
    }
    factory {
        CareSchemaStepsTouchHelperCallback()
    }
    factory {
        ChangeSchemaNameUseCase(
            actions = get(),
            careSchemaDao = get()
        )
    }
    factory {
        DeleteSchemaUseCase(
            actions = get(),
            careSchemaDao = get()
        )
    }
    factory {
        AddSchemaStepUseCase(
            actions = get(),
            careSchemaStepDao = get()
        )
    }
    factory {
        DeleteSchemaStepUseCase(
            actions = get(),
            careSchemaStepDao = get(),
            updateStepsOrderUseCase = get()
        )
    }
    factory {
        UpdateStepsOrderUseCase(
            careSchemaStepDao = get()
        )
    }
    factory {
        UseCaseActions(
            dialogService = get()
        )
    } binds arrayOf(
        AddSchemaStepUseCase.Actions::class,
        ChangeSchemaNameUseCase.Actions::class,
        DeleteSchemaStepUseCase.Actions::class,
        DeleteSchemaUseCase.Actions::class
    )
}
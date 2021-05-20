package com.example.edit_care_schema

import com.example.edit_care_schema.use_case.*
import com.example.edit_care_schema.use_case.AddSchemaStepUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaStepsUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaStepUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val careSchemaDetailsModule = module {
    viewModel { (careSchemaId: Int) ->
        EditCareSchemaViewModel(
            careSchemaId = careSchemaId,
            careSchemaRepo = get(),
            changeSchemaName = get(),
            changeSchemaSteps = get(),
            deleteSchema = get(),
            addSchemaStep = get(),
            deleteSchemaStep = get()
        )
    }
    factory {
        ChangeSchemaNameUseCase(
            careSchemaRepo = get()
        )
    }
    factory {
        ChangeSchemaStepsUseCase(
            careSchemaRepo = get()
        )
    }
    factory {
        AddSchemaStepUseCase(
            careSchemaRepo = get()
        )
    }
    factory {
        DeleteSchemaUseCase(
            careSchemaRepo = get()
        )
    }
    factory {
        DeleteSchemaStepUseCase(
            careSchemaRepo = get()
        )
    }
    factory { (callback: CareSchemaStepsAdapter.Callback) ->
        CareSchemaStepsAdapter(
            callback = callback,
            careSchemaStepsTouchHelperCallback = get()
        )
    }
    factory {
        CareSchemaStepsTouchHelperCallback()
    }
}
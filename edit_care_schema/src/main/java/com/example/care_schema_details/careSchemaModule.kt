package com.example.care_schema_details

import com.example.care_schema_details.components.EditCareSchemaViewModel
import com.example.care_schema_details.components.CareSchemaStepsAdapter
import com.example.care_schema_details.components.CareSchemaStepsTouchHelperCallback
import com.example.care_schema_details.use_case.AddSchemaStepUseCase
import com.example.care_schema_details.use_case.ChangeSchemaNameUseCase
import com.example.care_schema_details.use_case.ChangeSchemaStepsUseCase
import com.example.care_schema_details.use_case.DeleteSchemaUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val careSchemaDetailsModule = module {
    viewModel { (careSchemaId: Int) ->
        EditCareSchemaViewModel(
            careSchemaId = careSchemaId,
            careSchemaRepo = get(),
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
    factory { parameters ->
        CareSchemaStepsAdapter(
            boundActivityEdit = parameters.get<EditCareSchemaActivity>() as EditCareSchemaActivity,
            careSchemaStepsTouchHelperCallback = get()
        )
    }
    factory {
        CareSchemaStepsTouchHelperCallback()
    }
}
package com.example.care_schema_details

import com.example.care_schema_details.components.CareSchemaDetailsViewModel
import com.example.care_schema_details.components.CareSchemaStepsAdapter
import com.example.care_schema_details.components.CareSchemaStepsTouchHelperCallback
import com.example.care_schema_details.use_case.AddSchemaStepUseCase
import com.example.care_schema_details.use_case.ChangeSchemaNameUseCase
import com.example.care_schema_details.use_case.ChangeSchemaStepsUseCase
import com.example.care_schema_details.use_case.DeleteCareSchemaUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val careSchemaDetailsModule = module {
    viewModel { (careSchemaId: Int) ->
        CareSchemaDetailsViewModel(
            careSchemaId = careSchemaId,
            careSchemaRepo = get(),
            changeSchemaNameUseCase = get(),
            changeSchemaStepsUseCase = get(),
            deleteCareSchemaUseCase = get()
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
        DeleteCareSchemaUseCase(
            careSchemaRepo = get()
        )
    }
    factory { parameters ->
        CareSchemaStepsAdapter(
            boundActivity = parameters.get<CareSchemaDetailsActivity>() as CareSchemaDetailsActivity,
            viewModel = parameters.get<CareSchemaDetailsViewModel>() as CareSchemaDetailsViewModel,
            careSchemaStepsTouchHelperCallback = get()
        )
    }
    factory {
        CareSchemaStepsTouchHelperCallback()
    }
}
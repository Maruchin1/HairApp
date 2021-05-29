package com.example.edit_care_schema

import com.example.corev2.navigation.EditCareSchemaDestination
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
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val editCareSchemaModule = module {
    viewModel {
        EditCareSchemaViewModel(get(), get(), get(), get(), get(), get())
    }
    factory(named(EditCareSchemaDestination.ACTIVITY)) {
        EditCareSchemaActivity::class.java
    }
    factory {
        CareSchemaStepsAdapter(androidContext(), get())
    }
    factory {
        CareSchemaStepsTouchHelperCallback()
    }
    factory {
        ChangeSchemaNameUseCase(get(), get())
    }
    factory {
        DeleteSchemaUseCase(get(), get())
    }
    factory {
        AddSchemaStepUseCase(get(), get())
    }
    factory {
        DeleteSchemaStepUseCase(get(), get(), get())
    }
    factory {
        UpdateStepsOrderUseCase(get())
    }
    factory {
        UseCaseActions(get())
    } binds arrayOf(
        AddSchemaStepUseCase.Actions::class,
        ChangeSchemaNameUseCase.Actions::class,
        DeleteSchemaStepUseCase.Actions::class,
        DeleteSchemaUseCase.Actions::class
    )
}
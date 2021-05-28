package com.example.edit_care_schema

import com.example.corev2.navigation.EditCareSchemaDestination
import com.example.edit_care_schema.actions.AddSchemaStepActions
import com.example.edit_care_schema.actions.ChangeSchemaNameActions
import com.example.edit_care_schema.actions.DeleteSchemaActions
import com.example.edit_care_schema.actions.DeleteSchemaStepActions
import com.example.edit_care_schema.components.CareSchemaStepsAdapter
import com.example.edit_care_schema.components.CareSchemaStepsTouchHelperCallback
import com.example.edit_care_schema.components.DestinationImpl
import com.example.edit_care_schema.components.EditCareSchemaViewModel
import com.example.edit_care_schema.use_case.*
import com.example.edit_care_schema.use_case.AddSchemaStepUseCase
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaStepUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaUseCase
import com.example.edit_care_schema.use_case.UpdateStepsOrderUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editCareSchemaModule = module {
    viewModel {
        EditCareSchemaViewModel(get(), get(), get(), get(), get(), get())
    }
    factory<EditCareSchemaDestination> {
        DestinationImpl()
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
    factory<ChangeSchemaNameUseCase.Actions> {
        ChangeSchemaNameActions(get())
    }
    factory {
        DeleteSchemaUseCase(get(), get())
    }
    factory<DeleteSchemaUseCase.Actions> {
        DeleteSchemaActions(get())
    }
    factory {
        AddSchemaStepUseCase(get(), get())
    }
    factory<AddSchemaStepUseCase.Actions> {
        AddSchemaStepActions(get())
    }
    factory {
        DeleteSchemaStepUseCase(get(), get(), get())
    }
    factory<DeleteSchemaStepUseCase.Actions> {
        DeleteSchemaStepActions(get())
    }
    factory {
        UpdateStepsOrderUseCase(get())
    }
}
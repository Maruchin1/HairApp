package com.example.edit_care_schema

import com.example.corev2.navigation.EditCareSchemaDestination
import com.example.edit_care_schema.components.CareSchemaStepsAdapter
import com.example.edit_care_schema.components.CareSchemaStepsTouchHelperCallback
import com.example.edit_care_schema.components.DestinationImpl
import com.example.edit_care_schema.components.EditCareSchemaViewModel
import com.example.edit_care_schema.use_case.ChangeSchemaNameUseCase
import com.example.edit_care_schema.use_case.DeleteSchemaUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editCareSchemaModule = module {
    viewModel {
        EditCareSchemaViewModel(get(), get(), get(), get(), get())
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
        ChangeSchemaNameUseCase(
            dialogService = get(),
            careSchemaDao = get(),
            changeSchemaNameTitle = androidContext().getString(R.string.change_care_schema_name)
        )
    }
    factory {
        DeleteSchemaUseCase(
            dialogService = get(),
            careSchemaDao = get()
        )
    }
}
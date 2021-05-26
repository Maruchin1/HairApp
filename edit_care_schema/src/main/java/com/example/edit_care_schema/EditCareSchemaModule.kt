package com.example.edit_care_schema

import com.example.corev2.navigation.EditCareSchemaDestination
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editCareSchemaModule = module {
    factory<EditCareSchemaDestination> {
        DestinationImpl()
    }
    factory {
        CareSchemaStepsAdapter(androidContext(), get())
    }
    factory {
        CareSchemaStepsTouchHelperCallback()
    }
    viewModel {
        EditCareSchemaViewModel(get(), get())
    }
}
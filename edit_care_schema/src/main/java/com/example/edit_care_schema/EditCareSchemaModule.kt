package com.example.edit_care_schema

import com.example.corev2.navigation.EditCareSchemaDestination
import com.example.edit_care_schema.use_case.DeleteSchemaUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editCareSchemaModule = module {
    viewModel {
        EditCareSchemaViewModel(get(), get(), get(), get())
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
        DeleteSchemaUseCase(get(), get())
    }
}
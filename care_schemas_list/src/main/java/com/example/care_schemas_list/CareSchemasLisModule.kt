package com.example.care_schemas_list

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val careSchemasListModule = module {
    viewModel {
        CareSchemasListViewModel(get(), get())
    }
    factory {
        SchemasAdapter(androidContext(), get())
    }
    factory {
        SchemaStepsAdapter(androidContext())
    }
}
package com.example.care_schemas_list

import com.example.navigation.DestinationType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val careSchemasListModule = module {
    viewModel {
        CareSchemasListViewModel(
            careSchemaDao = get(),
            careSchemaDetailsDestination = get(named(DestinationType.CARE_SCHEMA_DETAILS))
        )
    }
    factory {
        SchemasAdapter(
            context = androidContext(),
            stepsAdapter = get()
        )
    }
    factory {
        SchemaStepsAdapter(
            context = get()
        )
    }
}
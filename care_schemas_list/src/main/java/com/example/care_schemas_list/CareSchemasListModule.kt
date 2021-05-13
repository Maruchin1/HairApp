package com.example.care_schemas_list

import com.example.core.domain.CareSchema
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val careSchemasListModule = module {
    viewModel {
        CareSchemasListViewModel(
            careSchemaRepo = get()
        )
    }
    factory { (onSchemaClicked: (schema: CareSchema) -> Unit) ->
        SchemasAdapter(
            onSchemaClicked = onSchemaClicked,
            context = androidContext(),
            stepsAdapter = get()
        )
    }
    factory {
        SchemaStepsAdapter()
    }
}
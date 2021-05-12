package com.example.care_schemas_list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val careSchemasListModule = module {
    viewModel {
        CareSchemasListViewModel(
            careSchemaRepo = get()
        )
    }
}
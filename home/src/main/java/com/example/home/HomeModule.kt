package com.example.home

import com.example.core.domain.CareSchema
import com.example.home.care_schemas.CareSchemasListViewModel
import com.example.home.care_schemas.SchemaStepsAdapter
import com.example.home.care_schemas.SchemasAdapter
import com.example.home.use_case.AddCareSchemaUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val homeModule = module {
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
    factory {
        AddCareSchemaUseCase(
            careSchemaRepo = get()
        )
    }
}
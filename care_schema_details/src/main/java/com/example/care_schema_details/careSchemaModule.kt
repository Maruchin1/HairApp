package com.example.care_schema_details

import com.example.care_schema_details.components.CareSchemaDetailsViewModel
import com.example.care_schema_details.use_case.ChangeSchemaNameUseCase
import com.example.care_schema_details.use_case.DeleteCareSchemaUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val careSchemaDetailsModule = module {
    viewModel { (careSchemaId: Int) ->
        CareSchemaDetailsViewModel(
            careSchemaId = careSchemaId,
            careSchemaRepo = get(),
            changeSchemaNameUseCase = get(),
            deleteCareSchemaUseCase = get()
        )
    }
    factory {
        ChangeSchemaNameUseCase(
            careSchemaRepo = get()
        )
    }
    factory {
        DeleteCareSchemaUseCase(
            careSchemaRepo = get()
        )
    }
}
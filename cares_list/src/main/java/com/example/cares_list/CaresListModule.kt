package com.example.cares_list

import com.example.cares_list.components.CaresAdapter
import com.example.cares_list.components.CaresListViewModel
import com.example.cares_list.components.UseCaseActions
import com.example.cares_list.use_case.AddNewCareUseCase
import com.example.navigation.DestinationType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val caresListModule = module {
    viewModel {
        CaresListViewModel(
            careDao = get(),
            clockService = get(),
            addNewCareUseCase = get(),
            careDetailsDestination = get(named(DestinationType.CARE_DETAILS))
        )
    }
    factory {
        CaresAdapter()
    }
    factory {
        AddNewCareUseCase(
            actions = get(),
            clockService = get(),
            careDao = get(),
            careStepDao = get()
        )
    }
    factory {
        UseCaseActions(
            dialogService = get()
        )
    } binds arrayOf(
        AddNewCareUseCase.Actions::class
    )
}
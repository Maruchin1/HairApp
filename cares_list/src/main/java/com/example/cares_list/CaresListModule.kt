package com.example.cares_list

import com.example.cares_list.components.CaresAdapter
import com.example.cares_list.components.CaresListViewModel
import com.example.cares_list.components.UseCaseActions
import com.example.cares_list.use_case.OpenAddNewCareUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val caresListModule = module {
    viewModel {
        CaresListViewModel(get(), get(), get())
    }
    factory {
        CaresAdapter()
    }
    factory {
        OpenAddNewCareUseCase(get(), get())
    }
    factory {
        UseCaseActions(get())
    } binds arrayOf(
        OpenAddNewCareUseCase.Actions::class
    )
}
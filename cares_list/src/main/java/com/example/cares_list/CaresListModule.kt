package com.example.cares_list

import com.example.cares_list.components.CaresAdapter
import com.example.cares_list.components.CaresListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val caresListModule = module {
    viewModel {
        CaresListViewModel(get(), get(), get())
    }
    factory {
        CaresAdapter()
    }
}
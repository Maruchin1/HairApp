package com.example.products_list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productsListModule = module {
    viewModel {
        ProductsListViewModel(get(), get())
    }
    factory {
        ProductsAdapter()
    }
}
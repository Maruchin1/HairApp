package com.example.products_list

import com.example.navigation.DestinationType
import com.example.products_list.model.AddNewProductUseCase
import com.example.products_list.model.ProductsListViewModel
import com.example.products_list.ui.ActionsHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val productsListModule = module {
    viewModel {
        ProductsListViewModel(
            productDao = get(),
            actions = get(),
            addNewProductUseCase = get(),
        )
    }
    factory {
        AddNewProductUseCase(
            actions = get(),
            productDao = get()
        )
    }
    single {
        ActionsHandler(
            productDetailsDestination = get(named(DestinationType.PRODUCT_DETAILS))
        )
    } binds arrayOf(
        ProductsListViewModel.Actions::class,
        AddNewProductUseCase.Actions::class
    )
}
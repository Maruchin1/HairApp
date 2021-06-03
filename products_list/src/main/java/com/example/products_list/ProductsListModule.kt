package com.example.products_list

import com.example.navigation.DestinationType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val productsListModule = module {
    viewModel {
        ProductsListViewModel(
            productDao = get(),
            productDetailsDestination = get(named(DestinationType.PRODUCT_DETAILS))
        )
    }
    factory {
        ProductsAdapter()
    }
}
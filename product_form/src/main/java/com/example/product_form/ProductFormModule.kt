package com.example.product_form

import com.example.navigation.DestinationType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val productFormModule = module {
    viewModel {
        ProductFormViewModel(
            form = get(),
            productDao = get()
        )
    }
    factory(named(DestinationType.PRODUCT_DETAILS)) {
        ProductFormActivity::class.java
    }
    factory {
        ProductForm()
    }
}
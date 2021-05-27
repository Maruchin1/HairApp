package com.example.product_form

import com.example.corev2.navigation.ProductFormDestination
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productFormModule = module {
    viewModel {
        ProductFormViewModel(get(), get(), get())
    }
    factory<ProductFormDestination> {
        DestinationImpl()
    }
    factory {
        ProductForm()
    }
}
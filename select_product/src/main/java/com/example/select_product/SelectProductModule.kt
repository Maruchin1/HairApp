package com.example.select_product

import com.example.navigation.DestinationType
import com.example.select_product.components.SelectProductActivity
import com.example.select_product.components.SelectProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val selectProductModule = module {
    viewModel {
        SelectProductViewModel(
            productDao = get()
        )
    }
    factory(named(DestinationType.SELECT_PRODUCT)) {
        SelectProductActivity::class.java
    }
}
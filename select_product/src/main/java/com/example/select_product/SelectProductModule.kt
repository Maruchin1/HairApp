package com.example.select_product

import com.example.corev2.navigation.SelectProductDestination
import com.example.select_product.components.SelectProductActivity
import com.example.select_product.components.SelectProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val selectProductModule = module {
    viewModel {
        SelectProductViewModel(get())
    }
    factory(named(SelectProductDestination.ACTIVITY)) {
        SelectProductActivity::class.java
    }
}
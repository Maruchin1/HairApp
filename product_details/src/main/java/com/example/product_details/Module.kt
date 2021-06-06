package com.example.product_details

import com.example.navigation.DestinationType
import com.example.product_details.ui.ProductDetailsActivity
import com.example.product_details.model.ProductDetailsViewModel
import com.example.product_details.ui.ActionsHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val productDetailsModule = module {
    viewModel {
        ProductDetailsViewModel(
            actions = get(),
            productDao = get(),
        )
    }
    factory(named(DestinationType.PRODUCT_DETAILS)) {
        ProductDetailsActivity::class.java
    }
    single {
        ActionsHandler(
            dialogService = get(),
            captureProductPhotoDestination = get(named(DestinationType.CAPTURE_PRODUCT_PHOTO)),
        )
    } binds arrayOf(
        ProductDetailsViewModel.Actions::class
    )
}
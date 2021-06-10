package com.example.product_details

import com.example.navigation.DestinationType
import com.example.product_details.model.*
import com.example.product_details.model.CaptureProductPhotoUseCase
import com.example.product_details.model.DeleteProductUseCase
import com.example.product_details.model.ProductDetailsViewModel
import com.example.product_details.model.RemoveProductPhotoUseCase
import com.example.product_details.model.UpdateProductUseCase
import com.example.product_details.ui.ProductDetailsActivity
import com.example.product_details.ui.ActionsHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val productDetailsModule = module {
    factory(named(DestinationType.PRODUCT_DETAILS)) {
        ProductDetailsActivity::class.java
    }
    viewModel {
        ProductDetailsViewModel(
            productDao = get(),
            captureProductPhotoUseCase = get(),
            removeProductPhotoUseCase = get(),
            deleteProductUseCase = get(),
            updateProductUseCase = get()
        )
    }
    factory {
        CaptureProductPhotoUseCase(
            actions = get(),
            productDao = get()
        )
    }
    factory {
        DeleteProductUseCase(
            actions = get(),
            productDao = get()
        )
    }
    factory {
        RemoveProductPhotoUseCase(
            actions = get(),
            productDao = get()
        )
    }
    factory {
        UpdateProductUseCase(
            productDao = get()
        )
    }
    single {
        ActionsHandler(
            dialogService = get(),
            captureProductPhotoDestination = get(named(DestinationType.CAPTURE_PRODUCT_PHOTO)),
        )
    } binds arrayOf(
        CaptureProductPhotoUseCase.Actions::class,
        RemoveProductPhotoUseCase.Actions::class,
        DeleteProductUseCase.Actions::class
    )
}
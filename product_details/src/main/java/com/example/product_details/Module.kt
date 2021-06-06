package com.example.product_details

import com.example.navigation.DestinationType
import com.example.product_details.model.CaptureProductPhotoUseCase
import com.example.product_details.model.ChangeProductBasicInfoUseCase
import com.example.product_details.ui.ProductDetailsActivity
import com.example.product_details.model.ProductDetailsViewModel
import com.example.product_details.ui.UseCaseActions
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val productDetailsModule = module {
    viewModel {
        ProductDetailsViewModel(
            productDao = get(),
            captureProductPhotoUseCase = get(),
            changeProductBasicInfoUseCase = get()
        )
    }
    factory(named(DestinationType.PRODUCT_DETAILS)) {
        ProductDetailsActivity::class.java
    }
    factory {
        CaptureProductPhotoUseCase(
            actions = get(),
            productDao = get()
        )
    }
    factory {
        ChangeProductBasicInfoUseCase(
            productDao = get()
        )
    }
    single {
        UseCaseActions(
            captureProductPhotoDestination = get(named(DestinationType.CAPTURE_PRODUCT_PHOTO))
        )
    } binds arrayOf(
        CaptureProductPhotoUseCase.Actions::class
    )
}
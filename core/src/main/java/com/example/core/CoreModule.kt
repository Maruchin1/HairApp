package com.example.core

import com.example.core.use_case.*
import com.example.core.util.AppClock
import org.koin.dsl.module

val coreModule = module {

    factory { AddCare(get()) }

    factory { AddCareSchema(get()) }

    factory { AddProduct(get()) }

    factory { DeleteCare(get()) }

    factory { DeleteCarePhoto(get()) }

    factory { DeleteCareSchema(get()) }

    factory { DeleteProduct(get()) }

    factory { ShowCareSchemas(get()) }

    factory { ShowCaresList(get()) }

    factory { ShowPhotosReview(get()) }

    factory { ShowProductsList(get()) }

    factory { ShowProductsToSelect(get()) }

    factory { ShowSavedManufacturers(get()) }

    factory { ShowSelectedCare(get()) }

    factory { ShowSelectedProduct(get()) }

    factory { UpdateCare(get()) }

    factory { UpdateCareSchema(get()) }

    factory { UpdateProduct(get()) }

    factory { AppClock() }
}
package com.example.core

import com.example.core.use_case.*
import org.koin.dsl.module

val coreModule = module {

    factory {
        AddCare(careRepo = get())
    }

    factory {
        AddCareSchema(careSchemaRepo = get())
    }

    factory {
        AddProduct(productRepo = get())
    }

    factory {
        DeleteCare(careRepo = get())
    }

    factory {
        DeleteCarePhoto(careRepo = get())
    }

    factory {
        DeleteProduct(productRepo = get())
    }

    factory {
        ShowCareSchema(careSchemaRepo = get())
    }

    factory {
        ShowCareSchemas(careSchemaRepo = get())
    }

    factory {
        ShowCaresList(careRepo = get())
    }

    factory {
        ShowPhotosReview(careRepo = get())
    }

    factory {
        ShowProductsList(productRepo = get())
    }

    factory {
        ShowProductsToSelect(productRepo = get())
    }

    factory {
        ShowSavedManufacturers(productRepo = get())
    }

    factory {
        ShowSelectedCare(careRepo = get())
    }

    factory {
        ShowSelectedProduct(productRepo = get())
    }

    factory {
        UpdateCare(careRepo = get())
    }

    factory {
        UpdateCareSchema(careSchemaRepo = get())
    }

    factory {
        UpdateProduct(productRepo = get())
    }
}
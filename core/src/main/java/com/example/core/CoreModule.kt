package com.example.core

import com.example.core.use_case.*
import org.koin.dsl.module

val coreModule = module {

    factory {
        AddCare(careRepo = get())
    }

    factory {
        AddProduct(productRepo = get())
    }

    factory {
        DeleteCare(careRepo = get())
    }

    factory {
        DeleteProduct(productRepo = get())
    }

    factory {
        ShowCaresList(careRepo = get())
    }

    factory {
        ShowProductsList(productRepo = get())
    }

    factory {
        ShowProductsToSelect(productRepo = get())
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
        UpdateProduct(productRepo = get())
    }
}
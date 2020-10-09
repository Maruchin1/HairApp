package com.example.hairapp

import com.example.hairapp.page_care.CareViewModel
import com.example.hairapp.page_home.HomeViewModel
import com.example.hairapp.page_product.ProductViewModel
import com.example.hairapp.page_product_form.ProductFormViewModel
import com.example.hairapp.page_select_product.SelectProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        CareViewModel(
            showSelectedProduct = get(),
            showSelectedCare = get(),
            addCare = get(),
            updateCare = get(),
            deleteCare = get()
        )
    }

    viewModel {
        HomeViewModel(
            showProductsList = get(),
            showCaresList = get()
        )
    }

    viewModel {
        ProductViewModel(
            showSelectedProduct = get(),
            deleteProduct = get()
        )
    }

    viewModel {
        ProductFormViewModel(
            showSelectedProduct = get(),
            showProductApplicationOptions = get(),
            addProduct = get(),
            updateProduct = get()
        )
    }

    viewModel {
        SelectProductViewModel(
            showProductsToSelect = get()
        )
    }
}
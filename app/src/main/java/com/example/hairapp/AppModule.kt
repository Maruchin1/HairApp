package com.example.hairapp

import com.example.hairapp.framework.Dialog
import com.example.hairapp.page_care.CareViewModel
import com.example.hairapp.page_care_schemas.CareSchemasViewModel
import com.example.hairapp.page_cares_list.CaresListViewModel
import com.example.hairapp.page_edit_care_schema.EditCareSchemaViewModel
import com.example.hairapp.page_peh_balance.PehBalanceViewModel
import com.example.hairapp.page_photos_gallery.PhotosGalleryViewModel
import com.example.hairapp.page_product.ProductViewModel
import com.example.hairapp.page_product_form.ProductFormViewModel
import com.example.hairapp.page_products_ranking.ProductsRankingViewModel
import com.example.hairapp.page_select_product.SelectProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { Dialog() }

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
        CareSchemasViewModel(
            showCareSchemas = get(),
            addCareSchema = get()
        )
    }

    viewModel {
        EditCareSchemaViewModel(
            updateCareSchema = get(),
            deleteCareSchema = get()
        )
    }

    viewModel { CaresListViewModel(get(), get(), get()) }

    viewModel {
        PhotosGalleryViewModel(
            showPhotosReview = get(),
            deleteCarePhoto = get()
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
            showSavedManufacturers = get(),
            addProduct = get(),
            updateProduct = get()
        )
    }

    viewModel {
        SelectProductViewModel(
            showProductsToSelect = get()
        )
    }

    viewModel { PehBalanceViewModel(get(), get(), get()) }

    viewModel {
        ProductsRankingViewModel(
            careRepo = get(),
            appPreferences = get()
        )
    }
}
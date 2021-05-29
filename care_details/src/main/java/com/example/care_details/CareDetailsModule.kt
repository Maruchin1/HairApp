package com.example.care_details

import com.example.care_details.components.CareDetailsActivity
import com.example.care_details.components.CareDetailsViewModel
import com.example.corev2.navigation.CareDetailsDestination
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val careDetailsModule = module {
    viewModel {
        CareDetailsViewModel(get(), get())
    }
    factory(named(CareDetailsDestination.ACTIVITY)) {
        CareDetailsActivity::class.java
    }
}
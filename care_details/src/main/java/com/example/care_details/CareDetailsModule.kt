package com.example.care_details

import com.example.care_details.components.CareDetailsActivity
import com.example.care_details.components.CareDetailsViewModel
import com.example.care_details.components.UseCaseActions
import com.example.care_details.use_case.ChangeCareDateUseCase
import com.example.care_details.use_case.ChangeCareNotesUseCase
import com.example.care_details.use_case.DeleteCareUseCase
import com.example.care_details.use_case.SelectProductForStepUseCase
import com.example.corev2.navigation.CareDetailsDestination
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val careDetailsModule = module {
    viewModel {
        CareDetailsViewModel(get(), get(), get(), get(), get())
    }
    factory(named(CareDetailsDestination.ACTIVITY)) {
        CareDetailsActivity::class.java
    }
    factory {
        ChangeCareDateUseCase(get(), get())
    }
    factory {
        DeleteCareUseCase(get(), get())
    }
    factory {
        ChangeCareNotesUseCase(get())
    }
    factory {
        SelectProductForStepUseCase(get(), get())
    }
    single {
        UseCaseActions(get())
    } binds arrayOf(
        ChangeCareDateUseCase.Actions::class,
        DeleteCareUseCase.Actions::class,
        SelectProductForStepUseCase.Actions::class
    )
}
package com.example.care_details

import com.example.care_details.components.CareDetailsActivity
import com.example.care_details.components.CareDetailsViewModel
import com.example.care_details.components.UseCaseActions
import com.example.care_details.use_case.*
import com.example.care_details.use_case.AddCarePhotoUseCase
import com.example.care_details.use_case.ChangeCareDateUseCase
import com.example.care_details.use_case.ChangeCareNotesUseCase
import com.example.care_details.use_case.DeleteCareUseCase
import com.example.care_details.use_case.SelectProductForStepUseCase
import com.example.navigation.DestinationType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module

val careDetailsModule = module {
    viewModel {
        CareDetailsViewModel(
            careDao = get(),
            changeCareDateUseCase = get(),
            deleteCareUseCase = get(),
            selectProductForStepUseCase = get(),
            deleteCareStepUseCase = get(),
            addCareStepUseCase = get(),
            updateCareStepsOrderUseCase = get(),
            addCarePhotoUseCase = get(),
            changeCareNotesUseCase = get()
        )
    }
    factory(named(DestinationType.CARE_DETAILS)) {
        CareDetailsActivity::class.java
    }
    factory {
        ChangeCareDateUseCase(
            actions = get(),
            careDao = get()
        )
    }
    factory {
        DeleteCareUseCase(
            actions = get(),
            careDao = get()
        )
    }
    factory {
        ChangeCareNotesUseCase(
            careDao = get()
        )
    }
    factory {
        SelectProductForStepUseCase(
            actions = get(),
            careStepDao = get()
        )
    }
    factory {
        AddCareStepUseCase(
            actions = get(),
            careStepDao = get()
        )
    }
    factory {
        DeleteCareStepUseCase(
            actions = get(),
            careStepDao = get()
        )
    }
    factory {
        AddCarePhotoUseCase(
            actions = get(),
            carePhotoDao = get()
        )
    }
    factory {
        UpdateCareStepsOrderUseCase(
            careStepDao = get()
        )
    }
    single {
        UseCaseActions(
            dialogService = get(),
            selectProductDestination = get(named(DestinationType.SELECT_PRODUCT)),
            captureCarePhotoDestination = get(named(DestinationType.CAPTURE_CARE_PHOTO))
        )
    } binds arrayOf(
        ChangeCareDateUseCase.Actions::class,
        DeleteCareUseCase.Actions::class,
        SelectProductForStepUseCase.Actions::class,
        AddCareStepUseCase.Actions::class,
        DeleteCareStepUseCase.Actions::class,
        AddCarePhotoUseCase.Actions::class
    )
}
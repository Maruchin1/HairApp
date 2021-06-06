package com.example.product_details.ui

import com.example.navigation.CaptureProductPhotoDestination
import com.example.product_details.model.CaptureProductPhotoUseCase
import com.example.shared_ui.BaseActionsHandler

internal class UseCaseActions(
    private val captureProductPhotoDestination: CaptureProductPhotoDestination
) : BaseActionsHandler(),
    CaptureProductPhotoUseCase.Actions {

    override suspend fun captureProductPhoto(): String? {
        return activity?.let {
            captureProductPhotoDestination.navigate(it)
        }
    }
}
package com.example.product_details.ui

import com.example.corev2.ui.DialogService
import com.example.navigation.CaptureProductPhotoDestination
import com.example.product_details.R
import com.example.product_details.model.ProductDetailsViewModel
import com.example.shared_ui.BaseActionsHandler

internal class ActionsHandler(
    private val dialogService: DialogService,
    private val captureProductPhotoDestination: CaptureProductPhotoDestination
) : BaseActionsHandler(), ProductDetailsViewModel.Actions {

    override suspend fun captureProductPhoto(): String? =
        activity?.let {
            captureProductPhotoDestination.navigate(it)
        }


    override suspend fun confirmProductPhotoDeletion(): Boolean =
        activity?.let {
            dialogService.confirm(
                context = it,
                title = it.getString(R.string.delete_product_photo)
            )
        } ?: false

    override suspend fun confirmProductDeletion(): Boolean =
        activity?.let {
            dialogService.confirm(
                context = it,
                title = it.getString(R.string.delete_product)
            )
        } ?: false
}
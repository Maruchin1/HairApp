package com.example.product_details.model

import com.example.corev2.entities.Product

internal data class PageState(
    val product: Product? = null,
    val productDeleted: Boolean = false,
    val basicInfoMode: SectionMode = SectionMode.DISPLAY,
    val basicInfoForm: BasicInfoForm = BasicInfoForm(),
    val ingredientsMode: SectionMode = SectionMode.DISPLAY,
    val productApplicationsMode: SectionMode = SectionMode.DISPLAY
) {

    val hasBasicInfo: Boolean
        get() = !product?.name.isNullOrEmpty() && !product?.manufacturer.isNullOrEmpty()
}
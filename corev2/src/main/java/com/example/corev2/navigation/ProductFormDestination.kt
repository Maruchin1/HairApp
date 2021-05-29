package com.example.corev2.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class ProductFormDestination(override val activityClass: Class<*>) :
    Destination<ProductFormDestination.Params>() {

    @Parcelize
    data class Params(val editProductId: Long?) : Parcelable

    companion object {
        const val ACTIVITY = "product_form_activity"
    }
}
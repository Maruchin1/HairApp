package com.example.corev2.navigation

import android.content.Intent
import android.os.Parcelable
import com.example.corev2.entities.Product
import kotlinx.parcelize.Parcelize

class SelectProductDestination(
    override val activityClass: Class<*>
) : DestinationWithResult<SelectProductDestination.Params, Long?>() {

    override fun getResultFromIntent(intent: Intent): Long? {
        val result = intent.getLongExtra(SELECTED_PRODUCT_ID, -1)
        return if (result == -1L) null else result
    }

    @Parcelize
    data class Params(val productType: Product.Type?) : Parcelable

    companion object {
        const val ACTIVITY = "select_product_activity"
        const val SELECTED_PRODUCT_ID = "selected_product_id"
    }
}
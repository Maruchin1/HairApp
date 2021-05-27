package com.example.product_form

import android.app.Activity
import android.content.Intent
import com.example.corev2.navigation.ProductFormDestination

internal class DestinationImpl : ProductFormDestination() {

    override fun createIntent(originActivity: Activity, params: Params): Intent {
        return Intent(originActivity, ProductFormActivity::class.java)
            .putExtra(ProductFormActivity.EDIT_PRODUCT_ID, params.editProductId)
    }
}
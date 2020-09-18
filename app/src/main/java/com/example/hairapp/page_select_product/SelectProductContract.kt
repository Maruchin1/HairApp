package com.example.hairapp.page_select_product

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.core.domain.CareProduct

class SelectProductContract : ActivityResultContract<CareProduct, Pair<CareProduct, Int?>>() {

    private var requestedCareProduct: CareProduct? = null

    override fun createIntent(context: Context, input: CareProduct?): Intent {
        requestedCareProduct = input
        return Intent(context, SelectProductActivity::class.java)
            .putExtra(IN_APPLICATION_TYPE, input?.specificApplicationType)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<CareProduct, Int?> {
        val productId = if (resultCode == Activity.RESULT_OK) {
            val id = intent?.getIntExtra(OUT_PRODUCT_ID, -1)
            if (id == -1) null else id
        } else null
        val result = Pair(requestedCareProduct!!, productId)
        requestedCareProduct = null
        return result
    }

    companion object {
        const val IN_APPLICATION_TYPE = "in-application-tye"
        const val OUT_PRODUCT_ID = "out-product-id"
    }
}
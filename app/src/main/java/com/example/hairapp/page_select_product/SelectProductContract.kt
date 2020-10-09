package com.example.hairapp.page_select_product

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.core.domain.CareStep

class SelectProductContract : ActivityResultContract<CareStep, Pair<CareStep, Int?>>() {

    private var requestedCareStep: CareStep? = null

    override fun createIntent(context: Context, input: CareStep?): Intent {
        requestedCareStep = input
        return Intent(context, SelectProductActivity::class.java)
            .putExtra(IN_APPLICATION_TYPE, input?.specificApplicationType)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<CareStep, Int?> {
        val productId = if (resultCode == Activity.RESULT_OK) {
            val id = intent?.getIntExtra(OUT_PRODUCT_ID, -1)
            if (id == -1) null else id
        } else null
        val result = Pair(requestedCareStep!!, productId)
        requestedCareStep = null
        return result
    }

    companion object {
        const val IN_APPLICATION_TYPE = "in-application-tye"
        const val OUT_PRODUCT_ID = "out-product-id"
    }
}
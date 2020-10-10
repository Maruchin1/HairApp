package com.example.hairapp.page_select_product

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.core.domain.CareStep

class SelectProductContract : ActivityResultContract<SelectProductContract.Input, SelectProductContract.Output>() {

    private var requestedInput: Input? = null

    override fun createIntent(context: Context, input: Input?): Intent {
        requestedInput = input
        return Intent(context, SelectProductActivity::class.java)
            .putExtra(IN_CARE_TYPE, input?.type)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Output {
        val productId = if (resultCode == Activity.RESULT_OK) {
            val id = intent?.getIntExtra(OUT_PRODUCT_ID, -1)
            if (id == -1) null else id
        } else null
        val output = Output(requestedInput!!.position, requestedInput!!.type, productId)
        requestedInput = null
        return output
    }

    companion object {
        const val IN_CARE_TYPE = "in-care-tye"
        const val OUT_PRODUCT_ID = "out-product-id"
    }

    data class Input(val position: Int?, val type: CareStep.Type)

    data class Output(val position: Int?, val type: CareStep.Type, val productId: Int?)
}
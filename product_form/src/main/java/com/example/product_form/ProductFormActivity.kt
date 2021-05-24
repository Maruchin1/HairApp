package com.example.product_form

import android.os.Bundle
import com.example.corev2.ui.BaseActivity
import com.example.corev2.ui.SystemColors
import com.example.product_form.databinding.ActivityProductForm2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ProductFormActivity : BaseActivity<ActivityProductForm2Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_form2)
    }

    override fun bindActivity(): ActivityProductForm2Binding {
        return ActivityProductForm2Binding.inflate(layoutInflater)
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            allLight()
        }
    }

    companion object {
        const val EDIT_PRODUCT_ID = "editProductId"
    }
}
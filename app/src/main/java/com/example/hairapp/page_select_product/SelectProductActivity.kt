package com.example.hairapp.page_select_product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.core.domain.Product
import com.example.hairapp.R
import com.example.hairapp.common.ProductItemController
import com.example.hairapp.databinding.ActivitySelectProductBinding
import com.example.hairapp.framework.bind

class SelectProductActivity : AppCompatActivity(), ProductItemController {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivitySelectProductBinding>(R.layout.activity_select_product, null)
    }

    override fun onProductSelected(product: Product) {

    }
}
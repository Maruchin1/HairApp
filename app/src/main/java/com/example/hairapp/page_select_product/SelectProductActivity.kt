package com.example.hairapp.page_select_product

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.core.domain.Product
import com.example.core.domain.Application
import com.example.hairapp.R
import com.example.hairapp.common.ProductItemController
import com.example.hairapp.databinding.ActivitySelectProductBinding
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.framework.bind
import kotlinx.android.synthetic.main.activity_select_product.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectProductActivity : AppCompatActivity(), ProductItemController {

    private val viewModel: SelectProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivitySelectProductBinding>(R.layout.activity_select_product, viewModel)
        setupAdapter()
        selectProductApplicationType()
    }

    override fun onProductSelected(product: Product) {
        val data = Intent().putExtra(SelectProductContract.OUT_PRODUCT_ID, product.id)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun setupAdapter() {
        recycler.adapter = BindingRecyclerAdapter<Product>(
            controller = this,
            layoutResId = R.layout.item_product,
        ).apply {
            setItemComparator { it.id }
        }
    }

    private fun selectProductApplicationType() {
        val type =
            intent.getSerializableExtra(SelectProductContract.IN_APPLICATION_TYPE) as Application.Type?
        viewModel.selectProductApplicationType(type)
    }
}
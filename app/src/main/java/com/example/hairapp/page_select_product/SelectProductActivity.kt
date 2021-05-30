package com.example.hairapp.page_select_product

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import com.example.hairapp.R
import com.example.hairapp.common.ProductItemController
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.framework.SystemColors
import com.example.hairapp.framework.bindActivity
import com.example.hairapp.page_product_form.ProductFormActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectProductActivity : AppCompatActivity(), ProductItemController {

    private val viewModel: SelectProductViewModel by viewModel()

    fun addProduct() {
        val intent = ProductFormActivity.makeIntent(this, null)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemColors(this).allDark()
        setupAdapter()
        selectProductApplicationType()
    }

    override fun onProductSelected(product: Product) {
        val data = Intent().putExtra(SelectProductContract.OUT_PRODUCT_ID, product.id)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun setupAdapter() {
//        recycler.adapter = BindingRecyclerAdapter<Product>(
//            controller = this,
//            layoutResId = R.layout.item_product
//        ).apply {
//            setSource(viewModel.products, this@SelectProductActivity)
//            setItemComparator { it.id }
//        }
    }

    private fun selectProductApplicationType() {
        val type = intent.getSerializableExtra(SelectProductContract.IN_CARE_TYPE) as CareStep.Type?
        viewModel.selectProductApplicationType(type)
    }
}
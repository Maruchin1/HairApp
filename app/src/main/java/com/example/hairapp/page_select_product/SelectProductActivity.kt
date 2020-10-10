package com.example.hairapp.page_select_product

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.core.domain.CareStep
import com.example.core.domain.Product
import com.example.hairapp.R
import com.example.hairapp.common.ProductItemController
import com.example.hairapp.databinding.ActivitySelectProductBinding
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.framework.bind
import com.example.hairapp.framework.setNavigationColor
import com.example.hairapp.framework.setStatusBarColor
import com.example.hairapp.page_product_form.ProductFormActivity
import kotlinx.android.synthetic.main.activity_select_product.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectProductActivity : AppCompatActivity(), ProductItemController {

    private val viewModel: SelectProductViewModel by viewModel()

    fun addProduct() {
        val intent = ProductFormActivity.makeIntent(this, null)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivitySelectProductBinding>(R.layout.activity_select_product, viewModel)
        setStatusBarColor(R.color.color_primary)
        setNavigationColor(R.color.color_background)
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
            layoutResId = R.layout.item_product
        ).apply {
            setSource(viewModel.products, this@SelectProductActivity)
            setItemComparator { it.id }
        }
    }

    private fun selectProductApplicationType() {
        val type = intent.getSerializableExtra(SelectProductContract.IN_CARE_TYPE) as CareStep.Type?
        viewModel.selectProductApplicationType(type)
    }
}
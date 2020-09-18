package com.example.hairapp.page_select_product

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.core.domain.Product
import com.example.core.domain.ProductApplication
import com.example.hairapp.R
import com.example.hairapp.common.ProductItemController
import com.example.hairapp.databinding.ActivitySelectProductBinding
import com.example.hairapp.framework.RecyclerLiveAdapter
import com.example.hairapp.framework.bind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_select_product.*

@AndroidEntryPoint
class SelectProductActivity : AppCompatActivity(), ProductItemController {

    private val viewModel: SelectProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivitySelectProductBinding>(R.layout.activity_select_product, null)
        setupAdapter()
        selectProductApplicationType()
    }

    override fun onProductSelected(product: Product) {
        val data = Intent().putExtra(SelectProductContract.OUT_PRODUCT_ID, product.id)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun setupAdapter() {
        recycler.adapter = RecyclerLiveAdapter(
            controller = this,
            lifecycleOwner = this,
            layoutResId = R.layout.item_product,
            source = viewModel.products
        ).withItemComparator { it.id }
    }

    private fun selectProductApplicationType() {
        val type = intent.getSerializableExtra(SelectProductContract.IN_APPLICATION_TYPE) as ProductApplication.Type
        viewModel.selectProductApplicationType(type)
    }
}
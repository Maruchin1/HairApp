package com.example.hairapp.page_home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.hairapp.R
import com.example.hairapp.framework.RecyclerLiveAdapter
import com.example.hairapp.page_home.HomeViewModel
import com.example.hairapp.page_product.ProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products_list.*

@AndroidEntryPoint
class ProductsListFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()

    fun openProduct(productName: String) {
        val intent = Intent(requireContext(), ProductActivity::class.java)
            .putExtra(ProductActivity.EXTRA_PRODUCT_NAME, productName)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = RecyclerLiveAdapter.build(
            fragment = this,
            layoutResId = R.layout.item_product,
            source = viewModel.products,
            compareItemsBy = { it.productName }
        )
    }
}
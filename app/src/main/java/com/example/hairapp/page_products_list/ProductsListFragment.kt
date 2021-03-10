package com.example.hairapp.page_products_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.core.domain.Product
import com.example.hairapp.R
import com.example.hairapp.common.ProductItemController
import com.example.hairapp.framework.Binder
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.page_product.ProductActivity
import kotlinx.android.synthetic.main.fragment_products_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsListFragment : Fragment(), ProductItemController {

    private val viewModel: ProductsListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_products.adapter = BindingRecyclerAdapter<Product>(
            controller = this,
            layoutResId = R.layout.item_product
        ).apply {
            setSource(viewModel.alphabeticalProducts, viewLifecycleOwner)
        }

        viewModel.noProducts.observe(viewLifecycleOwner) {
            Binder.setVisibleOrGone(recycler_products, !it)
            Binder.setVisibleOrGone(no_products, it)
        }
    }

    override fun onProductSelected(product: Product) {
        val intent = ProductActivity.makeIntent(requireContext(), product.id)
        startActivity(intent)
    }
}
package com.example.hairapp.page_products_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.core.domain.Product
import com.example.hairapp.R
import com.example.hairapp.common.ProductItemController
import com.example.hairapp.databinding.FragmentProductsListBinding
import com.example.hairapp.framework.Binder
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.framework.bindFragment
import com.example.hairapp.page_product.ProductActivity
import com.example.hairapp.page_product_form.ProductFormActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsListFragment : Fragment(), ProductItemController {

    private val viewModel: ProductsListViewModel by viewModel()

    private var binding: FragmentProductsListBinding? = null

    fun openRanging() {

    }

    fun addProduct() {
        startActivity(ProductFormActivity.makeIntent(requireContext(), editProductId = null))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindFragment(inflater, container, R.layout.fragment_products_list, viewModel)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerProducts?.adapter = BindingRecyclerAdapter<Product>(
            controller = this,
            layoutResId = R.layout.item_product
        ).apply {
            setSource(viewModel.alphabeticalProducts, viewLifecycleOwner)
        }

        viewModel.noProducts.observe(viewLifecycleOwner) {
            Binder.setVisibleOrGone(binding!!.recyclerProducts, !it)
            Binder.setVisibleOrGone(binding!!.noProducts, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onProductSelected(product: Product) {
        val intent = ProductActivity.makeIntent(requireContext(), product.id)
        startActivity(intent)
    }
}
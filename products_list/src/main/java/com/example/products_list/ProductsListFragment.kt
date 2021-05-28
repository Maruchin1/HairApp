package com.example.products_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.InflateBinding
import com.example.corev2.ui.setVisibleOrGoneSource
import com.example.products_list.databinding.FragmentProductsListBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsListFragment : BaseFragment<FragmentProductsListBinding>(
    bindingInflater = FragmentProductsListBinding::inflate
) {

    private val viewModel: ProductsListViewModel by viewModel()
    private val productsAdapter: ProductsAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarMenu()
        setupNoProductsMessage()
        setupProductsRecycler()
    }

    private fun setupToolbarMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_product -> viewModel.onAddProductClick(requireActivity())
            }
            true
        }
    }

    private fun setupNoProductsMessage() {
        binding.noProducts.apply {
            icon.setImageResource(R.drawable.ic_round_shopping_basket_24)
            message.setText(R.string.no_products_message)
        }
        binding.noProducts.container.setVisibleOrGoneSource(
            viewModel.noProducts,
            viewLifecycleOwner
        )
    }

    private fun setupProductsRecycler() {
        binding.productsRecycler.adapter = productsAdapter
        productsAdapter.addSource(viewModel.products, viewLifecycleOwner)
    }
}
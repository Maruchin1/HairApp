package com.example.products_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setVisibleOrGoneSource
import com.example.products_list.databinding.FragmentProductsListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsListFragment : BaseFragment<FragmentProductsListBinding>() {

    private val viewModel: ProductsListViewModel by viewModels()

    @Inject
    internal lateinit var productsAdapter: ProductsAdapter

    override fun bindLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductsListBinding {
        return FragmentProductsListBinding.inflate(inflater, container, false)
    }

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
package com.example.products_list.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.corev2.entities.Product
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.BaseRecyclerAdapter
import com.example.corev2.ui.setPicassoUri
import com.example.corev2.ui.setVisibleOrGone
import com.example.products_list.R
import com.example.products_list.databinding.FragmentProductsListBinding
import com.example.products_list.databinding.ItemProductBinding
import com.example.products_list.model.PageState
import com.example.products_list.model.ProductsListViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsListFragment : BaseFragment<FragmentProductsListBinding>(
    bindingInflater = FragmentProductsListBinding::inflate
) {

    private val viewModel by viewModel<ProductsListViewModel>()
    private val actionsHandler by inject<ActionsHandler>()
    private val productsAdapter by lazy { ProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionsHandler.bind(requireActivity() as AppCompatActivity)
        setupToolbarMenu()
        setupNoProductsMessage()
        setupProductsRecycler()
        observeState()
    }

    private fun setupToolbarMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_product -> viewModel.onAddProductClicked()
            }
            true
        }
    }

    private fun setupNoProductsMessage() {
        binding.noProducts.apply {
            icon.setImageResource(R.drawable.ic_round_shopping_basket_24)
            message.setText(R.string.no_products_message)
        }
    }

    private fun setupProductsRecycler() {
        binding.productsRecycler.adapter = productsAdapter
    }

    private fun observeState() = lifecycleScope.launchWhenResumed {
        viewModel.state.collectLatest {
            updateNoProductsVisibility(it)
            updateAdapterData(it)
        }
    }

    private fun updateNoProductsVisibility(state: PageState) {
        binding.noProducts.root.setVisibleOrGone(state.noProducts)
    }

    private fun updateAdapterData(state: PageState) {
        productsAdapter.updateItems(state.products)
    }

    private inner class ProductsAdapter : BaseRecyclerAdapter<Product, ItemProductBinding>(
        bindingInflater = ItemProductBinding::inflate
    ) {

        override fun onSetItemData(binding: ItemProductBinding, item: Product) {
            binding.apply {
                image.setPicassoUri(item.photoData)
                productName.text = item.name
                manufacturer.text = item.manufacturer
                card.setOnClickListener { viewModel.onProductClicked(item.id) }
            }
        }
    }
}
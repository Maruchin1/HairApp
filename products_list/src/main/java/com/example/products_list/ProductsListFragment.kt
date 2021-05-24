package com.example.products_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setVisibleOrGone
import com.example.products_list.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListFragment : BaseFragment<FragmentTestBinding>() {

    private val viewModel: ProductsListViewModel by viewModels()

    override fun bindLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTestBinding {
        return FragmentTestBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNoProductsMessage()
        setupToolbarMenu()
    }

    private fun setupNoProductsMessage() {
        binding.noProducts.apply {
            icon.setImageResource(R.drawable.ic_round_shopping_basket_24)
            message.setText(R.string.no_products_message)
        }
        viewModel.noProducts.observe(viewLifecycleOwner) { noProducts ->
            binding.noProducts.container.setVisibleOrGone(noProducts)
        }
    }

    private fun setupToolbarMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_product -> viewModel.onAddProductClick(requireActivity())
            }
            true
        }
    }

}
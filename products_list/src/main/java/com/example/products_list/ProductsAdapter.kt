package com.example.products_list

import com.example.corev2.entities.Product
import com.example.corev2.ui.BaseRecyclerAdapter
import com.example.corev2.ui.InflateBinding
import com.example.corev2.ui.setPicassoUri
import com.example.products_list.databinding.ItemProductBinding

internal class ProductsAdapter : BaseRecyclerAdapter<Product, ItemProductBinding>(
    bindingInflater = ItemProductBinding::inflate
) {

    override fun onSetItemData(binding: ItemProductBinding, item: Product) {
        binding.apply {
            image.setPicassoUri(item.photoData)
            productName.text = item.name
            manufacturer.text = item.manufacturer
        }
    }
}
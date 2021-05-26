package com.example.products_list

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.corev2.entities.Product
import com.example.corev2.ui.BaseRecyclerAdapter
import com.example.corev2.ui.setPicassoUri
import com.example.products_list.databinding.ItemProductBinding

internal class ProductsAdapter :
    BaseRecyclerAdapter<Product, ItemProductBinding>() {

    override fun onBindItemView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemProductBinding {
        return ItemProductBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindItemData(binding: ItemProductBinding, item: Product) {
        binding.apply {
            image.setPicassoUri(Uri.parse(item.photoData))
            productName.text = item.name
            manufacturer.text = item.manufacturer
        }
    }
}
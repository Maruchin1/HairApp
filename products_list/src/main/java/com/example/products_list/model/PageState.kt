package com.example.products_list.model

import com.example.corev2.entities.Product

internal data class PageState(
    val products: List<Product> = emptyList()
) {

    val noProducts: Boolean
        get() = products.isEmpty()
}
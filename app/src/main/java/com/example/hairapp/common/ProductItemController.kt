package com.example.hairapp.common

import com.example.core.domain.Product

interface ProductItemController {

    fun onProductSelected(product: Product)
}
package com.example.core.domain

data class ProductType(
    var humectant: Boolean = false,
    var emollient: Boolean = false,
    var protein: Boolean = false
)
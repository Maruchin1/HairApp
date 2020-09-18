package com.example.core.domain

data class CareProduct(
    val specificApplicationType: ProductApplication.Type? = null,
    var product: Product? = null,
)
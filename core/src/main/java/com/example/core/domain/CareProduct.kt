package com.example.core.domain

data class CareProduct(
    val specificApplicationType: Application.Type? = null,
    var product: Product? = null,
)
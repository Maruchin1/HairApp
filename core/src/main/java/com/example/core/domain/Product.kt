package com.example.core.domain

data class Product(
    var name: String,
    var manufacturer: String,
    val type: ProductType,
    val application: MutableSet<String>,
    var photoData: String?
)
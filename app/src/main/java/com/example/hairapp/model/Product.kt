package com.example.hairapp.model

data class Product(
    var name: String,
    var manufacturer: String,
    val type: ProductType,
    val application: MutableSet<String>
)
package com.example.core.domain

data class ProductApplication(
    val name: String,
    val type: Type
) {

    enum class Type {
        CONDITIONER, SHAMPOO, OTHER
    }
}
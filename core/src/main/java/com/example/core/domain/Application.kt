package com.example.core.domain

data class Application(
    val name: String,
    val type: Type
) {

    enum class Type {
        CONDITIONER, SHAMPOO, OTHER
    }
}
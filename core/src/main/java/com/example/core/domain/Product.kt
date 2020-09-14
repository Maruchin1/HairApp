package com.example.core.domain

data class Product(
    val id: Int,
    var name: String,
    var manufacturer: String,
    val type: ProductType,
    var application: Set<String>,
    var photoData: String?
) {

    val typeNotSpecified: Boolean
        get() = arrayOf(type.humectants, type.emollients, type.proteins).all { !it }

    val applicationNotSpecified: Boolean
        get() = application.isEmpty()
}
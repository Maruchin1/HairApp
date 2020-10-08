package com.example.core.domain

data class Product(
    val id: Int,
    val type: Type,
    var name: String,
    var manufacturer: String,
    var applications: Set<Application>,
    var photoData: String?
) {

    val typeNotSpecified: Boolean
        get() = arrayOf(type.humectants, type.emollients, type.proteins).all { !it }

    val applicationNotSpecified: Boolean
        get() = applications.isEmpty()

    data class Type(
        var humectants: Boolean = false,
        var emollients: Boolean = false,
        var proteins: Boolean = false
    )
}
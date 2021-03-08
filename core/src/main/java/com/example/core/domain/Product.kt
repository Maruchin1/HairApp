package com.example.core.domain

data class Product(
    val id: Int,
    val composition: Composition = Composition(
        humectants = false,
        emollients = false,
        proteins = false
    ),
    var name: String = "",
    var manufacturer: String = "",
    var applications: Set<Application> = setOf(),
    var photoData: String? = null
) {

    val typeNotSpecified: Boolean
        get() = arrayOf(
            composition.humectants,
            composition.emollients,
            composition.proteins
        ).all { !it }

    val applicationNotSpecified: Boolean
        get() = applications.isEmpty()

    data class Composition(
        var humectants: Boolean = false,
        var emollients: Boolean = false,
        var proteins: Boolean = false
    )

    enum class Application {
        MILD_SHAMPOO,
        MEDIUM_SHAMPOO,
        STRONG_SHAMPOO,
        CONDITIONER,
        CREAM,
        MASK,
        LEAVE_IN_CONDITIONER,
        OIL,
        FOAM,
        SERUM,
        GEL,
        OTHER
    }
}
package com.example.core.domain

data class CareStep(
    val type: Type,
    var order: Int,
    var product: Product? = null,
) {

    enum class Type(val matchingApplications: List<Product.Application>) {
        CONDITIONER(listOf(Product.Application.CONDITIONER)),
        SHAMPOO(
            listOf(
                Product.Application.MILD_SHAMPOO,
                Product.Application.MEDIUM_SHAMPOO,
                Product.Application.STRONG_SHAMPOO
            )
        ),
        OIL(listOf(Product.Application.OIL)),
        EMULSIFIER(listOf(Product.Application.CONDITIONER)),
        STYLIZER(
            Product.Application.values().filterNot {
                it in listOf(
                    Product.Application.MILD_SHAMPOO,
                    Product.Application.MEDIUM_SHAMPOO,
                    Product.Application.STRONG_SHAMPOO,
                    Product.Application.CONDITIONER
                )
            }
        ),
        OTHER(Product.Application.values().toList())
    }
}
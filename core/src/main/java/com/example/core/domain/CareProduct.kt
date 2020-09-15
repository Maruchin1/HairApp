package com.example.core.domain

sealed class CareProduct {
    abstract val product: Product?

    sealed class Main : CareProduct() {

        data class Conditioner(override val product: Product?) : Main()

        data class Shampoo(override val product: Product?) : Main()
    }

    data class Extra(override val product: Product?) : CareProduct()
}
package com.example.core.domain

sealed class CareProduct {

    data class Before(val product: Product?) : CareProduct()

    sealed class Main : CareProduct() {

        data class Conditioner(val product: Product?) : Main()

        data class Shampoo(val product: Product?) : Main()
    }

    data class After(val product: Product?) : CareProduct()
}
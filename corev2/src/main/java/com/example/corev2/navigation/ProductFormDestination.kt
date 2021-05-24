package com.example.corev2.navigation

abstract class ProductFormDestination : Destination<ProductFormDestination.Params>() {

    data class Params(val editProductId: Long?)
}
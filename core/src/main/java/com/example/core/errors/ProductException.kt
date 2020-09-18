package com.example.core.errors

sealed class ProductException(message: String) : IllegalStateException(message) {

    class NotFound(productId: Int) : ProductException("Nie znaleziono produktu $productId")
}
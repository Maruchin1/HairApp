package com.example.core.use_case

sealed class ProductException(message: String) : IllegalStateException(message) {

    class NotFound(productId: Int) : ProductException("Nie znaleziono produktu $productId")

    class AlreadyExists(productName: String) : ProductException("Produkt o nazwie $productName już istnieje")
}
package com.example.core.use_case

sealed class ProductException(message: String) : IllegalStateException(message) {

    class NotFound(productName: String) : ProductException("Nie znaleziono produktu $productName")

    class AlreadyExists(productName: String) : ProductException("Produkt o nazwie $productName już istnieje")
}
package com.example.core.errors

sealed class CareException(message: String) : IllegalStateException(message) {

    class NotFound(careId: Int) : CareException("Nie znaleziono pielęgnacji o id: $careId")
}
package com.example.core.errors

sealed class CareSchemaException(message: String) : IllegalStateException(message) {

    class AlreadyExists(name: String) : CareSchemaException("Istnieje już inny schemat o nazwie $name")

    class NotFound() : CareSchemaException("Nie znaleziono wybranego schematu")
}
package com.example.core.domain

data class CareSchema(
    val id: Int,
    var name: String,
    var steps: List<CareSchemaStep>
) {
    companion object {
        val noSchema = CareSchema(
            id = -1,
            name = "Bez schematu",
            steps = listOf()
        )
    }
}
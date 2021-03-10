package com.example.core.domain

import java.time.LocalDate

data class Care(
    val id: Int,
    val schemaName: String = "",
    var date: LocalDate = LocalDate.now(),
    var photos: List<String> = listOf(),
    var steps: List<CareStep> = listOf(),
    var notes: String = ""
) {

    fun getStepsProducts(): List<Product> {
        return steps.mapNotNull { it.product }
    }
}
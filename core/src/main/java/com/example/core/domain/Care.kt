package com.example.core.domain

import java.time.LocalDate

data class Care(
    val id: Int,
    val schemaName: String,
    var date: LocalDate,
    var photos: List<String>,
    var steps: List<CareStep>
)
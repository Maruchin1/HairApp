package com.example.core.domain

import java.time.LocalDate

data class Care(
    val id: Int,
    var date: LocalDate,
    var photos: List<String>,
    var steps: List<CareStep>
)
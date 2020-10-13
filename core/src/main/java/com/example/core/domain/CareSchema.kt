package com.example.core.domain

data class CareSchema(
    val id: Int,
    var name: String,
    var steps: List<CareStep>
)
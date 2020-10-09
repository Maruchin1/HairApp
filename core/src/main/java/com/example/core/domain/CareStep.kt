package com.example.core.domain

data class CareStep(
    val specificApplicationType: Application.Type? = null,
    var order: Int,
    var product: Product? = null,
)
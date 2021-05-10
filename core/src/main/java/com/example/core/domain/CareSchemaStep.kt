package com.example.core.domain

data class CareSchemaStep(
    val type: CareStep.Type,
    var order: Int
) {

    fun toCareStep() = CareStep(type, order, null)
}
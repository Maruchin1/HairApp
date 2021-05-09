package com.example.care_schema_details

import com.example.core.domain.CareSchema
import com.example.core.domain.CareStep

fun createOmoCareSchema() = CareSchema(
    id = 1,
    name = "OMO",
    steps = listOf(
        CareStep(type = CareStep.Type.CONDITIONER, order = 1),
        CareStep(type = CareStep.Type.SHAMPOO, order = 2),
        CareStep(type = CareStep.Type.CONDITIONER, order = 3)
    )
)
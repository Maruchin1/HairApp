package com.example.care_schema_details

import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep

fun createOmoCareSchema() = CareSchema(
    id = 1,
    name = "OMO",
    steps = listOf(
        CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 1),
        CareSchemaStep(type = CareStep.Type.SHAMPOO, order = 2),
        CareSchemaStep(type = CareStep.Type.CONDITIONER, order = 3)
    )
)
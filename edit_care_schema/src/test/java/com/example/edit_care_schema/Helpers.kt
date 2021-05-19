package com.example.edit_care_schema

import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.core.domain.CareStep

fun createOmoCareSchema() = CareSchema(
    id = 1,
    name = "OMO",
    steps = listOf(
        CareSchemaStep(id = -1, type = CareStep.Type.CONDITIONER, order = 1),
        CareSchemaStep(id = -1, type = CareStep.Type.SHAMPOO, order = 2),
        CareSchemaStep(id = -1, type = CareStep.Type.CONDITIONER, order = 3)
    )
)
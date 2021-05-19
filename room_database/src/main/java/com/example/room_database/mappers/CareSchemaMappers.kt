package com.example.room_database.mappers

import com.example.core.domain.CareSchema
import com.example.core.domain.CareSchemaStep
import com.example.room_database.entities.CareSchemaEntity
import com.example.room_database.entities.CareSchemaStepEntity

internal fun CareSchema.toEntity() = CareSchemaEntity(id, name)

internal fun CareSchemaEntity.toDomain(steps: List<CareSchemaStepEntity>) =
    CareSchema(id, name, steps.map { it.toDomain() })

internal fun CareSchemaStep.toEntity(careSchemaId: Int) =
    CareSchemaStepEntity(id, type, order, careSchemaId)

internal fun CareSchemaStepEntity.toDomain() = CareSchemaStep(id, type, order)
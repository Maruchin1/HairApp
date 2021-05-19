package com.example.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.entity.CareSchemaEntity
import com.example.data.entity.CareSchemaStepEntity

internal data class CareSchemaWithSteps(

    @Embedded
    val careSchema: CareSchemaEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "careSchemaId"
    )
    val steps: List<CareSchemaStepEntity>
)